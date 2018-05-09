var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                this.dateTime = this.dateTime.replace("T", " ").substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax":{
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            data.exceed? $(row).attr("data-mealExceed", true) : $(row).attr("data-mealExceed", false);
        },
        "initComplete": makeEditable
    });

    $("#startDate, #endDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d"
    });

    $("#startTime, #endTime").datetimepicker({
        datepicker: false,
        format: "H:i"
    });

    $("#dateTime").datetimepicker({
        format: "Y-m-d H:i"
    });

});