$(document).ready(function() {

    function exportTableToCSV($table, filename) {

        var $rows = $table.find('tr:has(td)'),

            // Temporary delimiter characters unlikely to be typed by keyboard
            // This is to avoid accidentally splitting the actual contents
            tmpColDelim = String.fromCharCode(11), // vertical tab character
            tmpRowDelim = String.fromCharCode(0), // null character

            // actual delimiter characters for CSV format
            colDelim = '","',
            rowDelim = '"\r\n"',

            // Grab text from table into CSV formatted string
            csv = '"' + $rows.map(function(i, row) {
                var $row = $(row),
                    $cols = $row.find('td');

                return $cols.map(function(j, col) {
                    var $col = $(col),
                        text = $col.text();

                    return text.replace(/"/g, '""'); // escape double quotes

                }).get().join(tmpColDelim);

            }).get().join(tmpRowDelim)
                .split(tmpRowDelim).join(rowDelim)
                .split(tmpColDelim).join(colDelim) + '"';


        if (false && window.navigator.msSaveBlob) {

            var blob = new Blob([decodeURIComponent(csv)], {
                type: 'text/csv;charset=utf8'
            });

            window.navigator.msSaveBlob(blob, filename);

        } else if (window.Blob && window.URL) {
            // HTML5 Blob
            var blob = new Blob([csv], {
                type: 'text/csv;charset=utf-8'
            });
            var csvUrl = URL.createObjectURL(blob);

            $(this)
                .attr({
                    'download': filename,
                    'href': csvUrl
                });
        } else {

            var csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

            $(this)
                .attr({
                    'download': filename,
                    'href': csvData,
                    'target': '_blank'
                });
        }
    }


    $(".export").on('click', function(event) {
        // CSV
        var yesOrNo = confirm("Print all orders?");
        if(yesOrNo){
            var args = [$('#orders>table'), 'export.csv'];
            exportTableToCSV.apply(this, args);
            var today = new Date();

            var dd = today.getDate();
            var mm = today.getMonth()+1; //January is 0!
            var yyyy = today.getFullYear();
            var todayString = yyyy + "-" + mm + "-" + dd;


            fetch('http://193.191.177.8:10368/den-travak/orders?date=' + todayString ).then(response => response.json())
                .then(data => {
                    for(var i = 0; i < data.length; i++){

                        fetch('http://193.191.177.8:10368/den-travak/orders/' + data[i].id, {
                            method: 'PUT',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                "id": data[i].id,
                                "sandwichId": data[i].sandwichId,
                                "name": data[i].name,
                                "breadType": data[i].breadType,
                                "creationDate": data[i].creationDate,
                                "price": data[i].price,
                                "mobilePhoneNumber": data[i].mobilePhoneNumber,
                                "printed": true

                            }),
                        });
                    }
                })
        }
        else{
            return false;
        }



    });
});