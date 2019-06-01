package com.bolivarapp.Util;

import android.os.Environment;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Data {

    public static String obtenerNodoValor(String valor, Element elemento){
        Node node = (Node) elemento.getElementsByTagName(valor).item(0).getFirstChild();
        return node.getNodeValue();
    }

    public static ArrayList<String[]> getLista(File patch, String patchName) {
            ArrayList<String[]> listaRows = new ArrayList<String[]>();

            try{
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(new File(patch,patchName+".xml"));
                doc.getDocumentElement().normalize();

                NodeList nodosRow = doc.getElementsByTagName("Row");

                for (int i = 0; i < nodosRow.getLength(); i++) {
                    Node rowN = nodosRow.item(i);
                    if(rowN.getNodeType() == Node.ELEMENT_NODE){
                        Element rowE = (Element) rowN;

                        String a = obtenerNodoValor("pesos", rowE);
                        String b = obtenerNodoValor("t1", rowE);
                        String c = obtenerNodoValor("bsE", rowE);
                        String d = obtenerNodoValor("t2", rowE);
                        String e = obtenerNodoValor("bsT", rowE);

                        String []row = {a,b,c,d,e};
                        listaRows.add(row);
                    }
                }
            }
            catch(ParserConfigurationException parseE){}
            catch(SAXException saxE){}
            catch(IOException ioE){}

            return listaRows;
        }

    public static void makeXMLinit(String condicion,File patch){
            try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                DocumentBuilder builder = factory.newDocumentBuilder();
                DOMImplementation implementation = builder.getDOMImplementation();
                Document doc = implementation.createDocument(null, "doc_init", null);
                doc.setXmlVersion("1.0");

                doc.getDocumentElement().normalize();
                Node nodoRaiz = doc.getDocumentElement();

                Element condition = doc.createElement("element");
                condition.setTextContent(condicion);
                nodoRaiz.appendChild(condition);

                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                DOMSource sourse = new DOMSource(doc);
                File file = new File(patch, "/" + ("doc_init" +".xml"));
                StreamResult result = new StreamResult(file);
                transformer.transform(sourse, result);
            }
            catch(ParserConfigurationException parseE){}
            catch(TransformerConfigurationException transE){}
            catch(TransformerException transformE){}

        }

    public static void makeXML(ArrayList<String[]> lista, File patch, String patchName) {
            try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                DOMImplementation implementation = builder.getDOMImplementation();
                Document doc = implementation.createDocument(null, patchName, null);
                doc.setXmlVersion("1.0");

                doc.getDocumentElement().normalize();
                Node nodoRaiz = doc.getDocumentElement();

                for (int i = 0; i < lista.size(); i++) {
                    String row[] = lista.get(i);
                    Element newRow = doc.createElement("Row");

                    Element newPesos = doc.createElement("pesos");
                    newPesos.setTextContent(row[0]);
                    Element newT1 = doc.createElement("t1");
                    newT1.setTextContent(row[1]);
                    Element newBsE = doc.createElement("bsE");
                    newBsE.setTextContent(row[2]);
                    Element newT2 = doc.createElement("t2");
                    newT2.setTextContent(row[3]);
                    Element newBsT = doc.createElement("bsT");
                    newBsT.setTextContent(row[4]);

                    newRow.appendChild(newPesos);
                    newRow.appendChild(newT1);
                    newRow.appendChild(newBsE);
                    newRow.appendChild(newT2);
                    newRow.appendChild(newBsT);

                    nodoRaiz.appendChild(newRow);
                }

                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                DOMSource sourse = new DOMSource(doc);
                File file = new File(patch, "/" + (patchName +".xml"));
                StreamResult result = new StreamResult(file);
                transformer.transform(sourse, result);
            }
            catch(ParserConfigurationException parseE){}
            catch(TransformerConfigurationException transE){}
            catch(TransformerException transformE){}
        }

    public static String getDocInit(File patch) {
        String result = "start";

        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(patch,"doc_init.xml"));
            doc.getDocumentElement().normalize();
            result = doc.getElementsByTagName("element").item(0).getTextContent();
        }
        catch(ParserConfigurationException parseE){}
        catch(SAXException saxE){}
        catch(IOException ioE){}

        return result;
    }
}


