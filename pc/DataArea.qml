import QtQuick 2.6
import QtQuick.Controls 2.0

Item {
    property string epc
    property string type
    property string name
    property string stage
    property string status
    property string time
    property string location
    property string keeper
    property string note
    property string loanDate
    property string returnDate

    width: 100
    DataSheet {
        key: qsTr("EPC")
        value: parent.epc
        x: 10
        y: 10
    }
    Rectangle {
        x: 5
        y: 60
        width: 300
        height: 1
        color: "grey"
    }
    DataSheet {
        key: qsTr("Type")
        value: parent.type
        x: 10
        y: 90
    }
    DataSheet {
        key: qsTr("Name")
        value: parent.name
        x: 10
        y: 140
    }
    DataSheet {
        key: qsTr("Stage")
        value: parent.stage
        x: 10
        y: 190
    }
    DataSheet {
        key: qsTr("Status")
        value: parent.status
        x: 10
        y: 240
    }
    DataSheet {
        key: qsTr("Time")
        value: parent.time
        x: 10
        y: 290
    }
    DataSheet {
        key: qsTr("Location")
        value: parent.location
        x: 10
        y: 340
    }
    DataSheet {
        key: qsTr("Keeper")
        value: parent.keeper
        x: 10
        y: 390
    }
    Rectangle {
        x: 5
        y: 440
        width: 300
        height: 1
        color: "grey"
    }
    DataSheet {
        key: qsTr("Loan Date")
        value: parent.loanDate
        x: 10
        y: 470
    }
    DataSheet {
        key: qsTr("Return Date")
        value: parent.returnDate
        x: 10
        y: 520
    }
    DataSheet {
        key: qsTr("Note")
        value: parent.note
        x: 10
        y: 570
    }
}
