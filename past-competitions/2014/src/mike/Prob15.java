/**
 * CodeQuest 2014
 * Problem 15: Pretty Print
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob15 {
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
    private static final String PERIOD = ".";
    private static final String DECLARATION_START = "<?xml";
    private static final String DECLARATION_END = "?>";
    private static final String TAG_START = "<";
    private static final String END_TAG_START = "</";
    private static final String TAG_END = ">";
    private static final String SELF_CLOSING_TAG_END = "/>";
    private static final String NEXT_ELEMENT = "NEXT_ELEMENT";
    
    private static StringBuffer buf = new StringBuffer();
    private static int indentLevel = 0;
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                // make one big string from the input
                buf.append(inLine);
            }
            
            String xml = buf.toString();
            buf = new StringBuffer();
            
            // print the declaration and chop it off the front
            String declarationString = xml.substring(xml.indexOf(DECLARATION_START), (xml.indexOf(DECLARATION_END) + DECLARATION_END.length()));
            buf.append(declarationString + "\n");
            xml = xml.substring(xml.indexOf(DECLARATION_END) + DECLARATION_END.length());
            
            // find the start of the root element
            xml = xml.substring(xml.indexOf(TAG_START));
            XMLElement rootElement = null;
            XMLElement currentElement = null;
            
            // continue consuming until all start tags are gone
            while (xml.indexOf(TAG_START) == 0) {
                int nextEndTagIndex = xml.indexOf(TAG_END);
                int nextSelfClosingTagIndex = xml.indexOf(SELF_CLOSING_TAG_END);
                int endIndex = -1;
                
                if (nextSelfClosingTagIndex == (nextEndTagIndex-1)) {
                    // this is a self closing tag - create a new element
                    XMLElement newElement = new XMLElement();
                    
                    // consume the entire element text
                    endIndex = nextSelfClosingTagIndex + SELF_CLOSING_TAG_END.length();
                    newElement.addToken(xml.substring(0, endIndex));
                    
                    // set the parent and add to the current element
                    newElement.setParent(currentElement);
                    currentElement.addToken(newElement);
                } else {
                    // this is a start or an end tag - find out which
                    endIndex = nextEndTagIndex + TAG_END.length();
                    
                    if (xml.startsWith(END_TAG_START)) {
                        // end tag - just consume it and go up a level
                        currentElement.addToken(xml.substring(0, endIndex));
                        currentElement = currentElement.getParent();
                    } else {
                        // start tag - create a new element and go down a level
                        XMLElement newElement = new XMLElement();
                        if (rootElement != null) {
                            newElement.setParent(currentElement);
                            currentElement.addToken(newElement);
                        } else {
                            rootElement = newElement;
                            currentElement = newElement;
                        }
                        currentElement = newElement;
                        currentElement.addToken(xml.substring(0, endIndex));
                    }
                }
                
                if (xml.length() > endIndex) {
                    xml = xml.substring(endIndex);
                    
                    // consume anything after the next end tag and the next start tag after that
                    if (xml.indexOf(TAG_START) != 0) {
                        endIndex = xml.indexOf(TAG_START);
                        currentElement.addToken(xml.substring(0, endIndex));
                        if (xml.length() > endIndex) {
                            xml = xml.substring(endIndex);
                        } else {
                            xml = xml.substring(endIndex-1);
                        }
                    }
                } else {
                    xml = xml.substring(endIndex-1);
                }
            }
            
            rootElement.render();
            System.out.print(buf.toString());
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void indent() {
        for (int i=0; i<indentLevel; i++) {
            for (int j=0; j<4; j++) {
                buf.append(PERIOD);
            }
        }
    }
    
    public static class XMLElement {
        private ArrayList<Object> tokens = new ArrayList<Object>();
        private XMLElement parent = null;
        
        public void addToken(Object theObject) {
            tokens.add(theObject);
        }
        
        public void setParent(XMLElement theParent) {
            parent = theParent;
        }
        
        public XMLElement getParent() {
            return parent;
        }
        
        public void render() {
            // make a big string out of the element, marking the places of child elements
            StringBuffer elementBuf = new StringBuffer();
            ArrayList<XMLElement> childElements = new ArrayList<XMLElement>();
            int currentChildIndex = 0;
            for (int i=0; i<tokens.size(); i++) {
                Object currentObject = tokens.get(i);
                if (currentObject instanceof XMLElement) {
                    childElements.add((XMLElement) currentObject);
                    elementBuf.append(NEXT_ELEMENT);
                } else {
                    elementBuf.append(currentObject);
                }
            }
            String elementString = elementBuf.toString();
            
            // kill whitespace between nested elements
            String[] tokens = elementString.split(NEXT_ELEMENT);
            if (tokens.length > 1) {
                elementString = "";
                for (int i=0; i<tokens.length; i++) {
                    if (i>0) {
                        elementString += NEXT_ELEMENT;
                    }
                    elementString += tokens[i].trim();
                }
            }
            
            // print the start tag - assume the cursor is already in the right place
            int endIndex = elementString.indexOf(TAG_END) + TAG_END.length();
            String tagString = elementString.substring(0, endIndex);
            renderTag(tagString);
            
            // find out if there is more to do
            if (elementString.length() > endIndex) {
                // there is more to process, meaning this was not a self closing tag - chew up what we just used
                elementString = elementString.substring(endIndex);
                
                // find out if there are nested tags
                if (elementString.indexOf(NEXT_ELEMENT) >= 0) {
                    // nested content - increase the indent
                    indentLevel++;
                    newLine();
                    
                    int nextStartTagIndex = elementString.indexOf(TAG_START);
                    while (nextStartTagIndex != 0) {
                        indent();
                        
                        // either we have a nested element next or we have content
                        if (elementString.startsWith(NEXT_ELEMENT)) {
                            // another element is next
                            elementString = elementString.substring(NEXT_ELEMENT.length());
                            childElements.get(currentChildIndex).render();
                            currentChildIndex++;
                        } else {
                            // content - either there is another tag or there isn't
                            if (elementString.indexOf(NEXT_ELEMENT) == -1) {
                                // no more elements - go until the start of the next tag
                                nextStartTagIndex = elementString.indexOf(TAG_START);
                            } else {
                                // get the content between here and the next nested element
                                nextStartTagIndex = elementString.indexOf(NEXT_ELEMENT);
                            }
                            
                            String contentString = elementString.substring(0, nextStartTagIndex);
                            renderContent(contentString);
                            elementString = elementString.substring(nextStartTagIndex);
                            
                            // need a new line if the next thing is not the end tag
                            if (!elementString.startsWith(TAG_START)) {
//                                newLine();
                            }
                            newLine();
                        }
                        
                        // find the next start tag
                        nextStartTagIndex = elementString.indexOf(TAG_START);
                    }
                    
                    // now we should just have the end tag
                    indentLevel--;
                    indent();
                    renderTag(elementString);
                    newLine();
                } else {
                    // no nested content - render the text content
                    int nextStartTagIndex = elementString.indexOf(TAG_START);
                    String contentString = elementString.substring(0, nextStartTagIndex);
                    renderContent(contentString);
                    elementString = elementString.substring(nextStartTagIndex);
                    
                    // render the end tag
                    renderTag(elementString);
                    
                    newLine();
                }
            } else {
                // this was a self closing tag - go to the next line
                newLine();
            }
        }
        
        private void renderTag(String tagString) {
            int index = 0;
            
            char space = ' ';
            char lessThan = '<';
            char greaterThan = '>';
            char forwardSlash = '/';
            char equalSign = '=';
            
            // first print the <
            while (tagString.charAt(index) != lessThan) {
                index++;
            }
            buf.append(tagString.charAt(index));
            index++;
            tagString = tagString.substring(index);
            
            // see if this is an ending tag - if so, add the /
            index = 0;
            if (tagString.charAt(index) == forwardSlash) {
                buf.append(tagString.charAt(index));
                index++;
                tagString = tagString.substring(index);
            }
            
            // now skip spaces and add the element name
            index = 0;
            while (tagString.charAt(index) == space) {
                index++;
            }
            while ((tagString.charAt(index) != space) && (tagString.charAt(index) != greaterThan)) {
                buf.append(tagString.charAt(index));
                index++;
            }
            tagString = tagString.substring(index);
            
            // find out if there are attributes to worry about
            if (tagString.indexOf("\"") > -1) {
                // there are attributes - deal with them
                String[] stringArray = tagString.split("\"");
                for (int i=0; i<stringArray.length-1; i+=2) {
                    // space before attribute name
                    buf.append(space);
                    
                    // attribute name
                    String tempString = stringArray[i];
                    index = 0;
                    
                    // skip spaces before name
                    while (tempString.charAt(index) == space) {
                        index++;
                    }
                    while ((tempString.charAt(index) != space) && (tempString.charAt(index) != equalSign)) {
                        buf.append(Character.toLowerCase(tempString.charAt(index)));
                        index++;
                    }
                    
                    // equal sign and quote
                    buf.append("=\"");
                    
                    // value as is
                    buf.append(stringArray[i+1]);
                    
                    // end quote
                    buf.append("\"");
                }
                
                tagString = stringArray[stringArray.length-1];
            }
            
            // at this point there are no attributes - find out if this is self closing or not
            if (tagString.indexOf(SELF_CLOSING_TAG_END) > -1) {
                buf.append(space);
            }
            
            //ignore the rest of the spaces and print the ending sequence
            index = 0;
            while (tagString.charAt(index) == space) {
                index++;
            }
            buf.append(tagString.substring(index));
        }
        
        private void renderContent(String contentString) {
            contentString = contentString.trim();
            String[] stringArray = contentString.split("\\s+");
            for (int i=0; i<stringArray.length; i++) {
                if (i > 0) {
                    buf.append(" ");
                }
                buf.append(stringArray[i]);
            }
        }
        
        private void newLine() {
            buf.append("\n");
        }
    }
}
