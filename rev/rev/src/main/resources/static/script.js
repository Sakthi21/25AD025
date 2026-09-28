let quizData;
let attemptId;

function startQuiz() {

    let studentName = document.getElementById("name").value;
    let studentEmail = document.getElementById("email").value;

    if (studentName === "" || studentEmail === "") {
        alert("Enter name and email");
        return;
    }

    fetch("/api/attempts/quiz/1/start", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: studentName,
            email: studentEmail
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.id) {
                attemptId = data.id;
                loadQuiz();
            } else {
                alert("Cannot start quiz");
            }
        });
}

function loadQuiz() {

    fetch("/api/quizzes/1")
        .then(response => response.json())
        .then(data => {

            quizData = data;

            document.getElementById("start").style.display = "none";
            document.getElementById("quiz").style.display = "block";

            document.getElementById("title").innerText = data.title;

            let html = "";

            data.questions.forEach((q, i) => {

                html += `<div class="question">
                <b>${i + 1}. ${q.questionText}</b>`;

                q.answers.forEach(a => {
                    html += `
                    <div class="option">
                        <input type="radio"
                               name="q${q.id}"
                               value="${a.id}">
                        ${a.answerText}
                    </div>`;
                });

                html += `</div>`;
            });

            document.getElementById("questions").innerHTML = html;
        });
}

function submitQuiz() {

    let answers = [];

    quizData.questions.forEach(q => {

        let selected = document.querySelector(
            `input[name="q${q.id}"]:checked`
        );

        answers.push({
            questionId: q.id,
            answerId: selected ? Number(selected.value) : null
        });
    });

    fetch("/api/attempts/" + attemptId + "/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            answers: answers
        })
    })
        .then(response => response.json())
        .then(data => {

            document.getElementById("quiz").style.display = "none";
            document.getElementById("result").style.display = "block";

            document.getElementById("score").innerText =
                "Score: " + data.score + " / " + data.totalQuestions;
        });
}