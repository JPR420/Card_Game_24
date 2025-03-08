# Card Game 24

A JavaFX-based card game where players create a mathematical expression using four random cards to achieve a result of 24.

## Getting Started

These instructions will guide you on how to set up and run the project locally.

### Prerequisites

- Java 11 or higher
- JavaFX
- An OpenAI API key (for hint functionality)

### Installing

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/CardGame24.git
    ```

2. Navigate to the project directory:
    ```bash
    cd CardGame24
    ```

3. Install any dependencies (if using a build tool like Maven or Gradle).

4. Set up your environment variable for the OpenAI API key:
    ```bash
    export APIKEY=your_openai_api_key
    ```

5. Run the application:
    ```bash
    mvn javafx:run
    ```

### Usage

The game will display four cards with numbers, and the user must input a mathematical expression using those numbers. The goal is to create an expression that evaluates to 24.

### License

This project is licensed under the MIT License.

## Acknowledgments

- OpenAI for providing the hint functionality.
- JavaFX for the UI components.
