function getToday(){
    var today = new Date();

    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();



    today = dd + '-' + mm + '-' + yyyy;
    var h1 = document.getElementById('date');
    h1.innerHTML = today;
    return today;

}



function getAllOrders(){
    fetch('http://193.191.177.8:10368/den-travak/orders').then(response => response.json())
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
    var p = document.getElementById("printed").innerHTML;

    for (var i = 0; i!= data.length; i++){
            var order = data[i];
            var orderDate = new Date(Date.parse(order.creationDate));


            var today = getToday();
    console.log(orderDate.toLocaleDateString());
            if(orderDate.toLocaleDateString() === today) {

                var ordernummer = i;
                ordernummer += 1;
                var row = ordersdiv.insertRow(i);

                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                var cell5 = row.insertCell(4);
                var cell6 = row.insertCell(5);


                cell1.innerHTML = order.id;
                cell1.className = "orderId";
                cell2.innerHTML = order.name;
                cell3.innerHTML = order.breadType;
                cell4.innerHTML = order.price;
                cell5.innerHTML = order.mobilePhoneNumber;
                cell6.innerHTML = order.printed;

                // if(order.printed){
                //     cell6.innerHTML = "true";
                //     cell6.style.color = "white";
                //     cell6.style.backgroundColor = "green";
                // }
                // else{
                //     cell6.innerHTML = "false";
                //     cell6.style.color = "white";
                //     cell6.style.backgroundColor = "red";
                // }


            }


    }



}