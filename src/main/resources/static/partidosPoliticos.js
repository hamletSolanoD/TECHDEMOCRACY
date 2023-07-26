(function ($) {
    $('#listapartidos').DataTable({
        "processing": true,
        "serverSide": true,
        "language": {
            "emptyTable": "No records to show"
        },
        columnDefs: [
            { orderable: false, targets: 0 }
        ],
        "ajax": {
            "url": '/rest/partidospoliticos',
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function (d) {
                return JSON.stringify(d);
            }
        },
        "order": [5, 'desc'],
    });
});