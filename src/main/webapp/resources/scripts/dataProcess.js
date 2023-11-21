

function borderRed(id) {
    let element = document.getElementById(id);
    element.classList.add("red-border");
    setTimeout(() => {
        element.classList.remove("red-border");
    }, 1500);
}


function onLoad(ev) {
    graphEntry();

}

window.addEventListener("load", onLoad);