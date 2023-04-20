/**
 * Represents a Dice object that has a random value.
 */
class Dice {
    constructor() {
        this.value = 1;
    }

    /**
     * Rolls the dice and sets a new random value between 1 and 6.
     */
    roll() {
        this.value = Math.floor(Math.random() * 6 + 1);
    }
}

/**
 * Represents a Dice Controller object that manages the rolling of five dice.
 */
class DiceController {
    /**
     * Constructs a new Dice Controller object with a root element and an array of five dice.
     * @param {string} root - The ID of the root element that contains the dice and the roll button.
     */
    constructor(root) {
        this.root = root;
        this.dices = Array(5).fill(null).map(() => new Dice());
        this.rollCount = 0; // Initialize roll counter to 0
    }

    /**
     * Runs the dice controller and adds event listeners to the roll button and the dice images.
     */
    run() {
        const rollButton = document.getElementById(this.root).querySelector("#roll-dice-btn");
        // Add an event listener to the roll button
        rollButton.addEventListener("click", () => {
            if (this.rollCount < 3) { // Only roll if less than 3 clicks
                this.rollDice();
                this.rollCount++;
                if (this.rollCount === 3) { // Disable the button after the third click
                    rollButton.disabled = true;
                }
            }
        }, true);

        // Add five dice images and event listeners to each image
        for (let i = 0; i < 5; i++) {
            let img = document.createElement("img");
            img.classList.add("animated-dice");
            img.src = this.imgLink(1); // Initialize the dice images
            document.getElementById("dice-container").appendChild(img);
            img.addEventListener("click", () => {
                if (this.rollCount !== 0) {
                    img.classList.toggle("selected"); // Toggle the selected class when clicked
                }
            });
        }
    }

    /**
     * Rolls the dice and updates the dice images with the new values.
     */
    rollDice() {
        // loads the mp3 and plays the roll dice sounds when button is clicked
        const audio = new Audio("/sound/rolling-dice.mp3");
        audio.play();

        clearTimeout(this.timeout);
        const diceImages = document.getElementById("dice-container").querySelectorAll(".animated-dice");
        diceImages.forEach((img) => {
            if (!img.classList.contains("selected")) {
                img.src = "/images/roll.gif";
            }
        });

        this.timeout = setTimeout(() => {
            diceImages.forEach((img, index) => {
                if (!img.classList.contains("selected")) {
                    this.dices[index].roll();
                    img.src = this.imgLink(this.dices[index].value);
                }
            });
        }, 1000);
    }

    /**
     * Gets the value of each dice as an array of integers.
     * @returns {Array} An array of integers representing the value of each dice.
     */
    getValue() {
        return this.dices.map(dice => dice.value);
    }

    /**
     * Returns the URL of the dice image based on the dice number.
     * @param {number} diceNumber - The value of the dice.
     * @returns {string} The URL of the dice image.
     */
    imgLink(diceNumber) {
        return `/images/dice_${diceNumber}.png`;
    }
}

/**
 * Adds an event listener to the finish round button to store the value of each dice in local storage.
 */
function clickOnFullRound(roundNumber) {
    const form = document.getElementById("finishRound");
    form.addEventListener('click', (event) => {
        event.preventDefault();
        const values = this.getValue();
        localStorage.setItem('diceValues', JSON.stringify(values));
        const json = JSON.stringify(values)
        if (roundNumber === 3) {
            showRoundAlert(roundNumber);
        }
    });
}


// Run the dice controller and add event listeners to the buttons
document.addEventListener("DOMContentLoaded", () => {
    // Create a new instance of the DiceController and run it
    const diceController = new DiceController("root");
    diceController.run();


// Add event listener for the finish round button
    const finishRoundButton = document.getElementById("finishRound");
    finishRoundButton.addEventListener("click", (event) => {
        event.preventDefault();
        if (diceController.rollCount > 0) { // Only submit if the roll button has been clicked at least once
            const diceValues = diceController.getValue();
            localStorage.setItem('diceValues', JSON.stringify(diceValues));
            const json = JSON.stringify(diceValues)

            // Create a form to send the data
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/roundController';
            // Create an input field to store the diceValues data
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'diceValues';
            input.id = 'diceValues';
            input.value = json;
            form.appendChild(input);

            const currentGameInput = document.createElement('input');
            currentGameInput.type = 'hidden';
            currentGameInput.name = 'currentGame';
            currentGameInput.value = sessionStorage.getItem('currentGame');
            form.appendChild(currentGameInput);

            // Submit the form
            document.body.appendChild(form);
            form.submit();
        } else {
            // Add a popup or message to tell the user to roll the dice first
            alert("Trill terningene først! :)");
        }
    });

});