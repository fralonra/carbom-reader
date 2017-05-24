import QtQuick 2.6
import QtQuick.Controls 1.4
import QtQuick.Dialogs 1.2
import QtQuick.Layouts 1.3
import QtQuick.Window 2.2

import CarbomReader 1.0

ApplicationWindow {
    visible: true
    width: Screen.desktopAvailableWidth
    height: Screen.desktopAvailableHeight
    title: database.title + qsTr("Carbom Reader")
    ToolBar {
        id: toolBar
        height: 80
        RowLayout {
            anchors.fill: parent
            spacing: 10
            MyToolButton {
                action: actOpen
            }
            MyToolButton {
                action: actSave
            }
            MyToolButton {
                action: actClose
                enabled: !database.isEmpty()
            }
            ToolBarSeparator {
            }
            MyToolButton {
                action: actInbound
            }
            MyToolButton {
                action: actOutbound
            }
            ToolBarSeparator {
            }
            MyToolButton {
                action: actLoan
                enabled: !database.isEmpty()
            }
            MyToolButton {
                action: actReturn
                enabled: database.isFound()
            }
            ToolBarSeparator {
            }
            MyToolButton {
                action: actFind
                enabled: !database.isEmpty()
            }
            MyToolButton {
                action: actReset
                enabled: database.isFound()
            }
            ToolBarSeparator {
            }
            MyToolButton {
                action: actFirst
                enabled: !database.isEmpty()
            }
            MyToolButton {
                action: actPrev
                enabled: !database.isEmpty()
            }
            MyToolButton {
                action: actNext
                enabled: !database.isEmpty()
            }
            MyToolButton {
                action: actLast
                enabled: !database.isEmpty()
            }
            Item {
                Layout.fillWidth: true
            }
            MyToolButton {
                action: actQuit
            }
        }
    }

    Action {
        id: actOpen
        text: qsTr("Open")
        iconSource: "image/open.png"
        shortcut: "Ctrl+O"
        onTriggered: fileDialog.open()
    }
    Action {
        id: actSave
        text: qsTr("Save")
        iconSource: "image/save.png"
        shortcut: "Ctrl+S"
        onTriggered: database.save()
    }
    Action {
        id: actClose
        text: qsTr("Close")
        iconSource: "image/close.png"
        shortcut: "Ctrl+W"
        onTriggered: database.close()
    }
    Action {
        id: actInbound
        text: qsTr("Inbound")
        iconSource: "image/inbound.png"
        shortcut: "Ctrl+I"
        onTriggered: inboundDialog.open()
    }
    Action {
        id: actOutbound
        text: qsTr("Outbound")
        iconSource: "image/outbound.png"
        shortcut: "Ctrl+U"
        onTriggered: outboundComfirm.open()
    }
    Action {
        id: actLoan
        text: qsTr("Loan")
        iconSource: "image/loan.png"
        shortcut: "Ctrl+L"
        onTriggered: loanDialog.open()
    }
    Action {
        id: actReturn
        text: qsTr("Return")
        iconSource: "image/return.png"
        shortcut: "Ctrl+R"
        onTriggered: returnDialog.open()
    }
    Action {
        id: actFirst
        text: qsTr("First")
        iconSource: "image/first.png"
        shortcut: "Ctrl+A"
        onTriggered: {
            table.currentRow = 0
            database.first()
        }
    }
    Action {
        id: actNext
        text: qsTr("Next")
        iconSource: "image/next.png"
        shortcut: "Ctrl+N"
        onTriggered: {
            if (table.currentRow < table.rowCount - 1)
                table.currentRow += 1
            database.next()
        }
    }
    Action {
        id: actPrev
        text: qsTr("Prev")
        iconSource: "image/prev.png"
        shortcut: "Ctrl+P"
        onTriggered: {
            if (table.currentRow > 0)
                table.currentRow -= 1
            database.prev()
        }
    }
    Action {
        id: actLast
        text: qsTr("Last")
        iconSource: "image/last.png"
        shortcut: "Ctrl+L"
        onTriggered: {
            table.currentRow = table.rowCount - 1
            database.last()
        }
    }
    Action {
        id: actFind
        text: qsTr("Find")
        iconSource: "image/find.png"
        shortcut: "Ctrl+F"
        onTriggered: findDialog.open()
    }
    Action {
        id: actReset
        text: qsTr("Reset")
        iconSource: "image/reset.png"
        shortcut: "Ctrl+R"
        onTriggered: database.reset()
    }
    Action {
        id: actQuit
        text: qsTr("Quit")
        iconSource: "image/quit.png"
        shortcut: "Ctrl+Q"
        onTriggered: Qt.quit()
    }

    FileDialog {
        id: fileDialog
        nameFilters: ["Data files (*.cbm)"]
        onAccepted: {
            database.file = fileUrl
        }
    }

    DialogEdit {
        id: findDialog
        title: qsTr("Find")
        onAccepted: database.find(entry)
    }

    DialogEdit {
        id: inboundDialog
        title: qsTr("Inbound")
        mode: "inbound"
        onAccepted: database.add(entry)
    }

    MessageDialog {
        id: outboundComfirm
        title: qsTr("Please Comfirm")
        text: qsTr("Are you sure to outbound this item?")
        standardButtons: StandardButton.Ok | StandardButton.Cancel
        onAccepted: {
            database.remove(table.currentRow)
        }
    }

    DialogLoan {
        id: loanDialog
        title: qsTr("Loan")
        source: database.list
        onAccepted: database.loan(selection, entry)
    }

    DialogReturn {
        id: returnDialog
        title: qsTr("Return")
        source: database.returnList
        onAccepted: database.returnBack(selection)
    }

    RowLayout {
        anchors.top: toolBar.bottom
        anchors.left: parent.left
        anchors.right: parent.right
        anchors.bottom: statusBar.top
        anchors.margins: 10
        spacing: 40
        TableView {
            id: table
            anchors.left: parent.left
            Layout.fillWidth: true
            Layout.fillHeight: true
            model: datamodel
            selectionMode: SelectionMode.NoSelection
            currentRow: database.position
            onCurrentRowChanged: database.setPosition(currentRow)
            TableViewColumn {
                role: "index"
                width: 30
                horizontalAlignment: Text.AlignHCenter
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "epc"
                title: qsTr("EPC")
                width: 200
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "type"
                title: qsTr("Type")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "name"
                title: qsTr("Name")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "stage"
                title: qsTr("Stage")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "status"
                title: qsTr("Status")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "time"
                title: qsTr("Time")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "location"
                title: qsTr("Location")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "keeper"
                title: qsTr("Keeper")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "note"
                title: qsTr("Note")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "loanDate"
                title: qsTr("Loan Date")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
            TableViewColumn {
                role: "returnDate"
                title: qsTr("Return Date")
                width: 100
                delegate: Rectangle {
                    color: styleData.row - 1=== table.currentRow ? "lightsteelblue" : "transparent"
                    Text {
                        text: styleData.value
                        color: "black"
                    }
                }
            }
        }
        DataArea {
            id: dataarea
            width: 400
            anchors.right: parent.right
            Layout.fillHeight: true
            epc: database.epc
            type: database.type
            name: database.name
            stage: database.stage
            status: database.status
            time: database.time
            location: database.location
            keeper: database.keeper
            note: database.note
            loanDate: database.loanDate
            returnDate: database.returnDate
        }
    }

    StatusBar {
        id: statusBar
        height: 20
        anchors.bottom: parent.bottom
        Label {
            text: qsTr("Total: ") + database.count +
                  qsTr("\t\tCurrent: ") + (database.position + 1) + qsTr("note")
        }
    }

    Data {
        id: database
    }

    DataModel {
        id: datamodel
        source: database.table
    }
}
