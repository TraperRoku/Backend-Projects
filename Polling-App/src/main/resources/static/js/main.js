var stompClient = null;
var username = null;

var usernamePage = document.querySelector('#username-page');
var votePage = document.querySelector('#vote-page');
var usernameForm = document.querySelector('#usernameForm');
var voteForm = document.querySelector('#voteForm');
var voteResults = document.querySelector('#voteResults');
var voteArea = document.querySelector('#voteArea');

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        votePage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/poll/public', function (message) {
                const voteCounts = JSON.parse(message.body);
                updateVoteResults(voteCounts);

            });
        });

        event.preventDefault();
    }
}

function updateVoteResults(voteCounts) {
    voteArea.innerHTML = '';

    for (var choice in voteCounts) {
        var count = voteCounts[choice];
        var listItem = document.createElement('li');
        listItem.textContent = choice + ': ' + count + ' votes';
        voteArea.appendChild(listItem);
    }

    voteResults.classList.remove('hidden');
}

function sendVote(event) {
    var selectedParty = document.querySelector('input[name="party"]:checked').value;
    stompClient.send("/app/poll.vote", {}, JSON.stringify({ choice: selectedParty }));
    event.preventDefault();
}

voteForm.addEventListener('submit', sendVote);
usernameForm.addEventListener('submit', connect);
