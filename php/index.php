<?php
// Database connection information
$servername = "localhost";
$username = "root";
$password = "";  
$dbname = "coderkube";  

// Creating the main connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Checking the connection stuff
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// simple array to storing the final data
$response = array();

// Get all questions from the database
$question_sql = "SELECT * FROM questions";
$question_result = $conn->query($question_sql);

if ($question_result->num_rows > 0) {
    while ($question_row = $question_result->fetch_assoc()) {
        // Get the options for each question
        $question_id = $question_row['question_id'];
        $options_sql = "SELECT * FROM quiz_options WHERE question_id = $question_id";
        $options_result = $conn->query($options_sql);

        $options = array();
        if ($options_result->num_rows > 0) {
            while ($option_row = $options_result->fetch_assoc()) {
                $option_data = array(
                    "option_id" => $option_row['option_id'],
                    "option" => $option_row['option_text'],
                    "correct_answer" => $option_row['correct_answer'],
                    "user_answer" => $option_row['user_answer']
                );
                array_push($options, $option_data);
            }
        }

        // Question data along with options
        $question_data = array(
            "question_id" => $question_row['question_id'],
            "question" => $question_row['question'],
            "correct_answer_option_id" => $question_row['correct_answer_option_id'],
            "quiz_options" => $options
        );
        array_push($response, $question_data);
    }
}

// Output as JSON
header('Content-Type: application/json');
echo json_encode($response);

// Close connection
$conn->close();
?>
