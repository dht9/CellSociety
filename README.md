## Cell Society

### Project Members:
* David Tran - Configuration
* Estelle He - Simulation 
* Ryan Chung - Visualization

### Timeline:
 * Date started: September 14, 2017
 * Date finished: October 2, 2017
 * Estimated number of hours: 100

### Resources Used:
 * ["Code Smells"](http://www.cs.duke.edu/courses/compsci308/current/readings/CodeSmells.pdf)
 * [Storing Data as an XML](http://code.makery.ch/library/javafx-8-tutorial/part5/)
 * [GridPane documentation](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html)
 * [Writing to XML in Java](https://www.journaldev.com/1112/how-to-write-xml-file-in-java-dom-parser)
 * [Parsing an XML in Java](https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm)
 * [Making Buttons from example__browser](https://coursework.cs.duke.edu/CompSci308_2017Fall/example_browser/blob/master/src/BrowserView.java)
 * ["Keep it DRY"](http://media.pragprog.com/articles/may_04_oo1.pdf)



### Files:
 * Starting the program: Main.java
 * Testing the program: Fire10x10.xml, FireRandom.xml
 * Error handling without crashing:
 	* **NumberFormatException** in XML
 	* **IOException** for XML loading
 	* **ParserConfigurationException** for XML parsing
* Resource file: Text.properties (in resources folder)

### Bugs:
Cell mouse-click works for changing color but not state.

### Extra features:
Two types of random features:

1. Loading random initial cell **states** (‘randomize’ button)
2. Loading random simulation **parameters** (ex: FireRandom.xml).




