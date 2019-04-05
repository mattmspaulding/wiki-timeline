# Wiki Timeline
Converts a Person's Wikipedia Page into a Timeline

## Main Screen
![alt text](https://github.com/mattmspaulding/wiki-timeline/blob/master/Screenshots/main-screen.png "Main Screen")
## Example using Einstein's Wikipedia Page
![alt text](https://github.com/mattmspaulding/wiki-timeline/blob/master/Screenshots/example-einstein-resized.png "Example using Einstein's Wiki page")

## Future Updates
* Sort dates happening before the year 0 (any BCE dates) properly
* Ensure each block is relevant to the person's life -- test for keywords
* Clean each sentence by removing Wikipedia's references (i.e. the bracketed numbers)
* Change the color of each circle, for each block, based on the sentence's sentiment
* Remove sentences that don't start with capital letters (a weird case broke the sentence splitter when a ? referred to an album's name in the middle of a sentence
* Remove sentences with multiple 4-digit numbers
* Create variables for birth/death years & move events past death (posthumous events) to new section
* Add more personality to each block -- alternate background colors

## Libraries
* JavaFX
* JSoup
* Stanford CoreNLP
