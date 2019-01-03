function getToday(){
    var today = new Date();

    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd = '0'+dd
    }

    if(mm<10) {
        mm = '0'+mm
    }

    today = dd + '-' + mm + '-' + yyyy;
    var h1 = document.getElementById('date');
    h1.innerHTML = today;
    return today;

}



function getAllOrders(){
    fetch('http://localhost:8080/orders').then(response => response.json())
        .then(data => {
            console.log(data) // Prints result from `response.json()` in getRequest
                VisualizeOrder(data);
            setTimeout(function (){getAllOrders()}, 3000);
        })


}

function VisualizeOrder(data){
    var div = document.getElementById('orders');
    var ordersdiv = document.getElementById('ordersTable');
    ordersdiv.innerHTML = "";

    for (var i = 0; i!= data.length; i++){
            var order = data[i];
            var orderDate = new Date(Date.parse(order.creationDate));


            var today = getToday();

            if(orderDate.toLocaleDateString() === today) {

                var ordernummer = i;
                ordernummer += 1;
                var row = ordersdiv.insertRow(i);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                var cell5 = row.insertCell(4);
                //var cell6 = row.insertCell(5);
                cell1.innerHTML = ordernummer;
                cell2.innerHTML = order.name;
                cell3.innerHTML = order.breadType;
                cell4.innerHTML = order.price;
                cell5.innerHTML = order.mobilePhoneNumber;

                ;

        }


    }



}