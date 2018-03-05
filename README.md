# Crochendo
Crochendo is a crafter's pocket-assistant that parses, analyzes, and displays crochet stitch-patterns in a responsive and engaging way. 
[note: Development is still in its initial stages of deployment. Work is currently being done to parse a scarf into simple text output. Features for displaying graphics, accommodating for "round" patterns, storing patterns in a SQLite database, querying the web for free patterns, and general aesthetic will be added in further implementations]

## Problem Statement:
Crochet in itself is pretty simple, but disheartened newcomers of the craft sometimes give up in frustration. Their stitch-count comes up short or an unfamiliar word discourages them. Even seasoned hobbyists can lose their place when revisiting a previous work. As confusion mounts, interest fades.

## Solution: 
The purpose of Crochendo is to bridge this gap over this trough of confusion by engaging the user in a detailed, step-by-step interface which provides immediate feedback on their progress as they work.
Rationale:
Any scarf viewed stitch-by-stitch can be seen as a list of instructions executed in-sequence until an end condition is reached. Sound familiar? Broken down, crochet and knitting patterns strongly resemble low-level code instructions. These instructions can be interpreted and transformed in a way that caters to the needs of a user, providing the necessary explanations at the right time with a tap or swipe of a finger.

## Architecture
### Overview/ Tools
| Feature                     | Implementation                                                  |
| --------------------------- |-----------------------------------------------------------------|
| Platform                    | Android(Java/XML)                                               |
| Testing                     | Junit                                                           |
| Data Acquisition & Storage  | jsoup, PDFBox [in progress], SQLite [in progress]               |
| Source Control              | Git/GitHub                                                      |

### Code Organization:
The architecture takes an Object-Oriented approach. A UML class diagram can be seen below. Each object's operations attempt to maximize cohesion and minimize coupling. Pattern objects (comprised of Rows of Stitch objects) are created by invoking a queue of Instruction objects (parsed and constructed via the Factory Method Design Pattern). Each Instruction object overrides methods to parse and execute raw instructions. After each Instruction is executed, they are placed into a list of processed Instructions for potential reuse. Organizing the code for constructing a Pattern object in this linear manner mirrors closely to how crochet patterns are constructed by-hand in the real world.
 
![Crochendo UML Class Diagram](https://github.com/charln2/Crochendo/blob/master/Crochendo_UML.png")
## Approach
After much brainstorming and research, a Test-Driven, Behavior-Driven Development approach was taken for this program. Each newly introduced test was ran alongside all existing test cases to see what refactoring needed to be done.
Initial test cases were derived through instructions of an existing scarf with simple instructions, listed below.
Due to the loose, sometimes whimsical style of human-written yarn instructions, initial testing was constrained by using the minimal number of stitches and instructions used to represent this work via text. After deriving a foundation architecture for these test cases, the program was expanded to include additional stitches and instructions catering to other raw instructions and stitches. Each test was named after the Instruction objects it tested and exceptions were thrown where it was thought to be helpful (null pointers, empty data structures, etc.). 
Tests accounting for graphics, "round" patterns, advance data storage, or querying the web were not addressed in the initial set of test cases, though light experimentation was done with graphics to assess feasibility.

## Implementation Details/ Design Decisions 
The majority of design decisions placed emphasis on usability and maintainability.

### Row Objects
Since stitch counts can change on a row-by-row basis, causing alignment of stitches to their anchors to shift, rows were implemented to optimize printing. Along with a "toString()" override for Row objects, Rows also implement a "toStringExpanded()" method that compresses any grouped stitches so that they align with their anchoring stitches on the row below when worked upon.

### Ambiguous decisions
As with any language, sometimes there can be multiple ways of saying the same thing, such as the "dc in 4th ch from hook" instruction when starting the first row after a series of chains.  There is an implied "ch-3" group on the new row made up of the skipped chains. The program makes this implicit conversion if detected, as it is a recurring pattern in the following rows.
![Chain Group Case](https://github.com/charln2/Crochendo/blob/master/chain_group_case.png")

## Aesthetic/ Themes
Crochendo's aesthetic is meant to be friendly and easy to use, resulting in a pastel teal color palette with red accents to invoke calm and focus. The obvious pun with "crescendo" being a gradual rise in loudness in music, is to act as a metaphor for slowly building a big(loud) piece of work from a series small(quiet) instructions. Plus, having a mascot bunny music conductor with a crochet hook in place of a conductor's wand would simply be adorable.
[note: Wireframes/ Redlines to come when available]

## Copyright
Abbreviations and graphics were adapted from standards made by the Craft Yarn Council (Source: Craft Yarn Council's www.YarnStandards.com)

Bellflower Infinity Scarf was designed by Carrie Carpenter
(Source: https://hooked-on-crafting.com/2013/09/10/bellflower-infinity-scarf-free-pattern/)
