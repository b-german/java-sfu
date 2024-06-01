package sfu.student.pr8;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Утилита для работы с XML
 */
public class XmlUtils {

  private static final String XML_VALIDATION_ERROR = """
      Возникла ошибка при валидации XML-файла! Настройки не будут применены.
      Допустимые значения для Cashiers [1;10]
      Допустимые значения для TimePerCustomer [3000;10000] в милисекундах
            
      Текст ошибки валидации: %s
      """;

  private XmlUtils() {
    throw new IllegalStateException("Utility class");
  }

  private static final String XSD_FILENAME = "settings.xsd";

  /**
   * Экспортировать настройки супермаркета в XML файл
   *
   * @param supermarketSettings настройки супермаркета
   * @param selectedFile        выбранный файл, куда экспортировать
   */
  static void exportToXml(SupermarketSettings supermarketSettings, File selectedFile) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();

      Element rootElement = document.createElement("Settings");
      document.appendChild(rootElement);

      Element cashiersElement = document.createElement("Cashiers");
      cashiersElement.appendChild(
          document.createTextNode(String.valueOf(supermarketSettings.cashierCount())));
      rootElement.appendChild(cashiersElement);

      Element timePerCustomerElement = document.createElement("TimePerCustomer");
      timePerCustomerElement.appendChild(
          document.createTextNode(String.valueOf(supermarketSettings.timePerCustomer())));
      rootElement.appendChild(timePerCustomerElement);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(selectedFile);
      transformer.transform(source, result);
    } catch (ParserConfigurationException | TransformerException e) {
      JOptionPane.showMessageDialog(null,
          "Возникла ошибка при выгрузке XML-файла!  %s".formatted(e.getMessage()),
          "Ошибка конфигурации",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Импортировать настройки супермаркета из файла
   *
   * @param selectedFile выбранный файл
   * @return настройки супермаркета
   */
  static SupermarketSettings importFromXml(File selectedFile) {
    if (!validateXML(selectedFile)) {
      return new SupermarketSettings(null, null);
    }

    Integer timePerCustomer = null;
    Integer cashierCount = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(selectedFile);

      NodeList nodeList = document.getDocumentElement().getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          if (element.getTagName().equals("Cashiers")) {
            cashierCount = Integer.parseInt((element.getTextContent()));
          } else if (element.getTagName().equals("TimePerCustomer")) {
            timePerCustomer = Integer.parseInt((element.getTextContent()));
          }
        }
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      JOptionPane.showMessageDialog(null,
          "Возникла ошибка при парсинге XML-файла! Настройки не будут применены. %s".formatted(
              e.getMessage()),
          "Ошибка конфигурации",
          JOptionPane.ERROR_MESSAGE);
    }
    return new SupermarketSettings(cashierCount, timePerCustomer);
  }

  /**
   * Валидирует формат XML файла по XSD схеме
   *
   * @param xmlFile файл для валидации
   * @return true, если файл валиден, иначе false
   */
  public static boolean validateXML(File xmlFile) {
    try {
      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = schemaFactory.newSchema(
          XmlUtils.class.getClassLoader().getResource(XSD_FILENAME));
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
      return true;
    } catch (SAXException | IOException e) {
      JOptionPane.showMessageDialog(null,
          XML_VALIDATION_ERROR.formatted(e.getMessage()),
          "Ошибка конфигурации",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

}
