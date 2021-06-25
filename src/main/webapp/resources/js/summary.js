document.addEventListener("DOMContentLoaded", function () {


    const btns = document.querySelectorAll("button");
    const categoryDiv = document.querySelector("#categories");
    const categoryInputs = categoryDiv.querySelectorAll("input");

    const institutionDiv = document.querySelector("#institutions");
    const institutuionInputs = institutionDiv.querySelectorAll("input")

    const quantityCategorySpan = document.querySelector("#quantityCategory");
    const summaryInstitutionSpan = document.querySelector("#summaryInstitution");
    const summaryStreetSpan = document.querySelector("#summaryStreet");
    const summaryCitySpan = document.querySelector("#summaryCity");
    const summaryZipCodeSpan = document.querySelector("#summaryZipCode");
    const summaryPhoneSpan = document.querySelector("#summaryPhone");
    const summaryDateSpan = document.querySelector("#summaryDate");
    const summaryTimeSpan = document.querySelector("#summaryTime");
    const summaryCommentsSpan = document.querySelector("#summaryComments");

    let street = "";
    const streetInput = document.querySelector("#street");
    let city = "";
    const cityInput = document.querySelector("#city");
    let zipcode = "";
    const zipCodeInput = document.querySelector("#zipCode");
    let phone = "";
    const phoneInput = document.querySelector("#phone");
    let date = "";
    const dateInput = document.querySelector("#date");
    let time = "";
    const timeInput = document.querySelector("#time");
    let details = "";
    const detailsInput = document.querySelector("#details");
    let quantity = 1;
    const quantityInput = document.querySelector("#quantity");
    let categories = [];
    let institution;

    btns.forEach(el => el.addEventListener("click", c => {

        street = streetInput.value;
        city = cityInput.value;
        zipcode = zipCodeInput.value;
        phone = phoneInput.value;
        date = dateInput.value;
        time = timeInput.value;
        details = detailsInput.value;
        quantity = quantityInput.value;

        let newCategories = [];
        categoryInputs.forEach(el => {

            if (el.checked === true) {
                const parentLabel = el.parentElement;
                const descriptionSpan = parentLabel.querySelector(".description");
                newCategories.push(descriptionSpan.innerHTML);

            }


        })

        categories = newCategories;
        console.log(categories);
        institutuionInputs.forEach(el => {
            if (el.checked === true) {
                const parentLabel = el.parentElement;
                const titleSpan = parentLabel.querySelector(".title");
                institution = titleSpan.innerHTML;
            }
        })

        let quantityStr = "1 worek : ";
        if (quantity > 1 && quantity < 5) {
            quantityStr = quantity + " worki : "
        }
        if (quantity >= 5) {
            quantityStr = quantity + " worków : "
        }

        categories.forEach(el => {
            quantityStr = quantityStr + " " + el + ","
        })
        quantityStr = quantityStr.slice(0, -1);

        quantityCategorySpan.innerHTML = quantityStr;
        if (institution == undefined) {
            summaryInstitutionSpan.innerHTML = "Nie wybrano organizacji"
        } else {
            summaryInstitutionSpan.innerHTML = "Dla: " + institution;
        }
        summaryStreetSpan.innerHTML = street;
        summaryCitySpan.innerHTML = city;
        summaryZipCodeSpan.innerHTML = zipcode;
        summaryPhoneSpan.innerHTML = phone;
        summaryDateSpan.innerHTML = date;
        summaryTimeSpan.innerHTML = time;
        details = details.trim();
        if (details.length > 0) {
            summaryCommentsSpan.innerHTML = details;
        } else {
            summaryCommentsSpan.innerHTML = "Brak uwag";
        }


        // categories.forEach(el=>{
        //     let span = document.createElement("span");
        //     span.innerHTML = el;
        //     span.classList.add("summary--text");
        //     quantityCategoryLi.append(span);
        // })


    }))


});