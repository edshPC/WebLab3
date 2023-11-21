let graphElement, graph;
let defaultR = 0;

function graphEntry() {
    graphElement = document.getElementById("graph");
    graph = Desmos.GraphingCalculator(graphElement, {
        keypad: false,
        expressions: false,
        zoomFit: false,
        settingsMenu: false,
        invertedColors: true,
        xAxisLabel: 'x',
        yAxisLabel: 'y',
        xAxisStep: 1,
        yAxisStep: 1,
        xAxisArrowMode: Desmos.AxisArrowModes.POSITIVE,
        yAxisArrowMode: Desmos.AxisArrowModes.POSITIVE
    });
    graphElement.addEventListener('click', handleClick);
    graphElement.addEventListener('mousemove', ev => {
        graphElement.moved = true;
    });
    graphElement.addEventListener('mousedown', ev => {
        graphElement.moved = false;
    });
    setBounds(3);
    makeGraph(defaultR)
}

const color = '#ff7000'; //'#008cff';

function makeGraph(r) {
    graph.setExpression({
        id: 'square',
        latex: `-${r}<=y<=0 \\{0<=x<=${r}\\}`,
        color,
        lines: false
    });
    graph.setExpression({
        id: 'triangle',
        latex: `0<=y<=${r}/2-x \\{x>=0\\}`,
        color,
        lines: false
    });
    graph.setExpression({
        id: 'circle',
        latex: `x^2+y^2<=(${r}/2)^2 \\{x<=0\\} \\{y<=0\\}`,
        color,
        lines: false
    });

}

var pointId = 0;
function drawPoint(x, y, res) {
    graph.setExpression({
        id: `p${pointId++}`,
        latex: `(${x}, ${y})`,
        color: res? '#bb00bb' : '#00bbbb',
        lines: false
    });
}

function setBounds(r) {
    let bounds = r*2;
    graph.setMathBounds({
        left: -bounds,
        right: bounds,
        bottom: -bounds,
        top: bounds
    });
}

function handleClick(ev) {
    if(graphElement.moved) return;

    let rect = graphElement.getBoundingClientRect();
    let x = ev.clientX - rect.left;
    let y = ev.clientY - rect.top;
    let mathCoordinates = graph.pixelsToMath({x, y});

    if (!inRectangle(mathCoordinates, graph.graphpaperBounds.mathCoordinates)) return;
    x = mathCoordinates.x.toFixed(2);
    y = mathCoordinates.y.toFixed(2);

    document.getElementById("selection:x-select_input").value = x;
    document.getElementById("selection:y-select").value = y;

    checkHit();
}

function inRectangle(point, rect) {
    return (
        point.x >= rect.left &&
        point.x <= rect.right &&
        point.y <= rect.top &&
        point.y >= rect.bottom
    )
}