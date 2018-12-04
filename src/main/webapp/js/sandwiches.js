function getSandwiches () {

    const div = document.getElementById('sandwiches');
    const url = 'http://localhost:8080/sandwiches';

    fetch(url)
        .then((resp) => resp.json())
        .then(function(data) {
            let sandwiches = data;
            return sandwiches.map(function(sandwich) {
                let card =  document.createElement('div');
                let cardBody =  document.createElement('div');
                let cardTitle =  document.createElement('h5');
                let cardPrice =  document.createElement('h6');
                let cardText =  document.createElement('p');
                let cardButton =  document.createElement('a');

                cardTitle.innerHTML = sandwich.name;
                cardPrice.innerHTML = "€" + sandwich.price.toFixed(2);
                cardText.innerHTML = sandwich.ingredients;
                cardButton.innerHTML = "Order sandwich";

                cardTitle.classList.add("card-title");
                cardPrice.classList.add("card-subtitle");
                cardPrice.classList.add("mb-2");
                cardPrice.classList.add("text-muted");
                cardText.classList.add("card-text");
                cardButton.classList.add("card-link");

                cardButton.href = "checkout.html?id=" + sandwich.id;

                cardBody.appendChild(cardTitle);
                cardBody.appendChild(cardPrice);
                cardBody.appendChild(cardText);
                cardBody.appendChild(cardButton);

                cardBody.classList.add("card-body");
                card.appendChild(cardBody);
                card.classList.add("card");
                div.appendChild(card);
            })
        })
        .catch(function(error) {
            console.log(error);
        });
}

function getSandwich(id) {

    const div = document.getElementById('sandwich');
    const url = 'http://localhost:8080/sandwiches/' + id;

    fetch(url)
        .then((resp) => resp.json())
        .then(function(data) {
            console.log("data:", data);
            let sandwiches = data;

                let card =  document.createElement('div');
                let cardBody =  document.createElement('div');
                let cardTitle =  document.createElement('h5');
                let cardPrice =  document.createElement('h6');
                let cardText =  document.createElement('p');
                let cardButton =  document.createElement('a');
                let radio1 = document.createElement('input');
                radio1.type = 'radio';
                radio1.name = 'breadtype';
                radio1.value='BOTERHAMMEKES';
                let radio1Name = document.createElement('label');
                radio1Name.innerHTML = '  Boterhammekes';
                let radio2 = document.createElement('input');
                radio2.type = 'radio';
                radio2.name = 'breadtype';
                radio2.value='TURK_BROOD';
                let radio2Name = document.createElement('label');
                radio2Name.innerHTML = '  Turks brood';
                let radio3 = document.createElement('input');
                radio3.type = 'radio';
                radio3.name = 'breadtype';
                radio3.value='WRAP';
                let radio3Name = document.createElement('label');
                radio3Name.innerHTML = '  Wrap';
                let phoneNumberLabel = document.createElement('label');
                phoneNumberLabel.innerHTML = "Mobile number";
                let phoneNumber = document.createElement('input');
                phoneNumber.type="text";

                cardTitle.innerHTML = data.name;
                cardPrice.innerHTML = "€" + data.price.toFixed(2);
                cardText.innerHTML = data.ingredients;
                cardButton.innerHTML = "Order sandwich";

                cardTitle.classList.add("card-title");
                cardPrice.classList.add("card-subtitle");
                cardPrice.classList.add("mb-2");
                cardPrice.classList.add("text-muted");
                cardText.classList.add("card-text");
                cardButton.classList.add("card-link");

                cardButton.addEventListener("click", addOrder(data))

                cardBody.appendChild(cardTitle);
                cardBody.appendChild(cardPrice);
                cardBody.appendChild(cardText);
                cardBody.appendChild(radio1);
                cardBody.appendChild(radio1Name);
                cardBody.appendChild(document.createElement('br'));
                cardBody.appendChild(radio2);
                cardBody.appendChild(radio2Name);
                cardBody.appendChild(document.createElement('br'));
                cardBody.appendChild(radio3);
                cardBody.appendChild(radio3Name);
                cardBody.appendChild(document.createElement('br'));
                cardBody.appendChild(phoneNumberLabel);
                cardBody.appendChild(document.createElement('br'));
                cardBody.appendChild(phoneNumber);
                cardBody.appendChild(document.createElement('br'));
                cardBody.appendChild(document.createElement('br'));
                cardBody.appendChild(cardButton);

                cardBody.classList.add("card-body");
                card.appendChild(cardBody);
                card.classList.add("card");
                div.appendChild(card);

        })
        .catch(function(error) {
            console.log(error);
        });
}

function addOrder(data) {

}

function getActiveId() {
    let search_params = this.getAllUrlParams(window.location.href);
    console.log(search_params.id);
    return search_params.id;
}

function getAllUrlParams(url) {

    // get query string from url (optional) or window
    var queryString = url ? url.split('?')[1] : window.location.search.slice(1);

    // we'll store the parameters here
    var obj = {};

    // if query string exists
    if (queryString) {

        // stuff after # is not part of query string, so get rid of it
        queryString = queryString.split('#')[0];

        // split our query string into its component parts
        var arr = queryString.split('&');

        for (var i = 0; i < arr.length; i++) {
            // separate the keys and the values
            var a = arr[i].split('=');

            // set parameter name and value (use 'true' if empty)
            var paramName = a[0];
            var paramValue = typeof (a[1]) === 'undefined' ? true : a[1];

            // (optional) keep case consistent
            paramName = paramName.toLowerCase();
            if (typeof paramValue === 'string') paramValue = paramValue.toLowerCase();

            // if the paramName ends with square brackets, e.g. colors[] or colors[2]
            if (paramName.match(/\[(\d+)?\]$/)) {

                // create key if it doesn't exist
                var key = paramName.replace(/\[(\d+)?\]/, '');
                if (!obj[key]) obj[key] = [];

                // if it's an indexed array e.g. colors[2]
                if (paramName.match(/\[\d+\]$/)) {
                    // get the index value and add the entry at the appropriate position
                    var index = /\[(\d+)\]/.exec(paramName)[1];
                    obj[key][index] = paramValue;
                } else {
                    // otherwise add the value to the end of the array
                    obj[key].push(paramValue);
                }
            } else {
                // we're dealing with a string
                if (!obj[paramName]) {
                    // if it doesn't exist, create property
                    obj[paramName] = paramValue;
                } else if (obj[paramName] && typeof obj[paramName] === 'string'){
                    // if property does exist and it's a string, convert it to an array
                    obj[paramName] = [obj[paramName]];
                    obj[paramName].push(paramValue);
                } else {
                    // otherwise add the property
                    obj[paramName].push(paramValue);
                }
            }
        }
    }

    return obj;
}