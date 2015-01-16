package com.bitcup.calligraphy.util;

import com.bitcup.calligraphy.domain.Lawha;
import com.bitcup.calligraphy.domain.Type;
import com.bitcup.calligraphy.repository.LawhaRepository;
import com.bitcup.calligraphy.spring.config.PropertyPlaceholderConfig;
import com.google.common.collect.Lists;
import org.apache.commons.lang.WordUtils;
import org.elasticsearch.common.collect.Sets;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author bitcup
 */
@Component
@Import(PropertyPlaceholderConfig.class)
public class AcDataParser {

    private static final Logger logger = LoggerFactory.getLogger(AcDataParser.class);
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Autowired
    private LawhaRepository lawhaRepository;

    @Value("${xml.filename}")
    private String filename;

    public void parse() throws Exception {
        File xml = new File(filename);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        List<String> artist = getStringList("str", "artist", xpath, document);
        List<String> imageSource = getStringList("str", "imageSource", xpath, document);
        List<String> name = getStringList("str", "name", xpath, document);
        List<String> sourceUrl = getStringList("str", "sourceUrl", xpath, document);

        /*
        List<String> website = getStringList("str", "website", xpath, document);
        List<String> description = getStringList("str", "description", xpath, document);
        List<Integer> height = getIntList("height", xpath, document);
        List<Integer> width = getIntList("width", xpath, document);
        */

        List<String> scripts = getStringList("arr", "scripts", xpath, document);
        List<String> styles = getStringList("arr", "styles", xpath, document);
        List<String> timestamp = getStringList("date", "timestamp", xpath, document);

        Set<String> stylesSet = Sets.newHashSet();
        Set<String> scriptsSet = Sets.newHashSet();

        for (int i = 0; i < artist.size(); i++) {
            Lawha l = new Lawha();
            l.setArtist(artist.get(i));
            l.setType(Type.WALL);
            DateTime dt = DATE_FORMATTER.parseDateTime(timestamp.get(i).substring(0, timestamp.get(i).indexOf("T")));
            l.setDateAdded(dt.toDate());
            l.setImgSrc("http://wall.arabiccalligraphy.com/static/lawha/detail" + imageSource.get(i));
            l.setName(name.get(i));
            l.setSource(sourceUrl.get(i));
            Set<String> tags = new TreeSet<String>();
            for (String s : scripts.get(i).split(",")) {
                if (!"other".equalsIgnoreCase(s.trim())) {
                    tags.add(WordUtils.capitalize(s.trim()));
                }
                scriptsSet.add(s.trim());
            }
            for (String s : styles.get(i).split(",")) {
                if ("classic".equalsIgnoreCase(s.trim())) {
                    s = "traditional";
                }
                tags.add(WordUtils.capitalize(s.trim()));
                stylesSet.add(s.trim());
            }
            l.setTags(tags);
            lawhaRepository.save(l);
            logger.info("saved {}", l);
        }
        logger.info("scripts {}", scriptsSet);
        logger.info("styles {}", stylesSet);

    }

    public void deleteAllData() {
        this.lawhaRepository.deleteAll();
        logger.info("deleted");
    }

    private List<String> getStringList(String prefix, String name, XPath xpath, Document document) throws Exception {
        String expression = "//doc/" + prefix + "[@name='" + name + "']";
        XPathExpression expr = xpath.compile(expression);
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentItem = nodes.item(i);
            //String key = currentItem.getAttributes().getNamedItem("name").getNodeValue();
            String value = currentItem.getTextContent();
            list.add(value);
        }
        logger.info("list of " + name + ":" + list.size());
        return list;
    }

    /*
    private List<Integer> getIntList(String name, XPath xpath, Document document) throws Exception {
        String expression = "//doc/int[@name='" + name + "']";
        XPathExpression expr = xpath.compile(expression);
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentItem = nodes.item(i);
            String value = currentItem.getTextContent();
            list.add(Integer.parseInt(value));
        }
        logger.info("list of " + name + ":" + list.size());
        return list;
    }
    */
}
