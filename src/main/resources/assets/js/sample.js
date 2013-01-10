$(document).ready(function() {
    $("#new-event-button").click(function() {
        $("#new-event-button").toggle();
        $("#new-event").toggle();
    });

    $("#new-event-cancel").click(function() {
        $("#new-event-button").toggle();
        $("#new-event").toggle();
    });

    $("#new-expense-button").click(function() {
        $("#new-expense-button").toggle();
        $("#new-expense").toggle();
    });

    $("#new-expense-cancel").click(function() {
        $("#new-expense-button").toggle();
        $("#new-expense").toggle();
    });

});