package com.bitcup.calligraphy;

import com.bitcup.calligraphy.domain.Lawha;
import com.bitcup.calligraphy.repository.LawhaRepository;
import com.bitcup.calligraphy.util.Constants;
import com.google.common.collect.Sets;
import org.apache.commons.lang.SerializationUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;

/**
 * @author bitcup
 */
@Component
public class LawhaDataGenerator {

    private SecureRandom random = new SecureRandom();
    private Lawha[] lawhat = new Lawha[5];

    @Autowired
    private LawhaRepository lawhaRepository;

    @PostConstruct
    private void init() {
        Lawha l1 = new Lawha();
        l1.setName("Wahdahu");
        l1.setImgSrc("http://wall.arabiccalligraphy.com/static/lawha/detail/2013/06/dplv9ThUqQ.jpg");
        l1.setTags(Sets.newHashSet("Thuluth", "Classic"));
        lawhat[0] = l1;

        Lawha l2 = new Lawha();
        l2.setName("Allah");
        l2.setImgSrc("http://wall.arabiccalligraphy.com/static/lawha/detail/2012/02/Z6cdAasf7w.jpg");
        l2.setTags(Sets.newHashSet("Farsi", "Classic"));
        lawhat[1] = l2;

        Lawha l3 = new Lawha();
        l3.setName("Hurriya");
        l3.setImgSrc("http://wall.arabiccalligraphy.com/static/lawha/detail/2013/06/Fo7Tobvuad.jpg");
        l3.setTags(Sets.newHashSet("Kufi", "Classic"));
        lawhat[2] = l3;

        Lawha l4 = new Lawha();
        l4.setName("Alhamd");
        l4.setImgSrc("http://wall.arabiccalligraphy.com/static/lawha/detail/2013/02/v7KPd8rUpI.jpg");
        l4.setTags(Sets.newHashSet("Thuluth", "Classic"));
        lawhat[3] = l4;

        Lawha l5 = new Lawha();
        l5.setName("Bismil");
        l5.setImgSrc("http://wall.arabiccalligraphy.com/static/lawha/detail/2012/02/SD2me0GQM9.jpg");
        l5.setTags(Sets.newHashSet("Turra", "Modern"));
        lawhat[4] = l5;
    }

    public void generateSampleData() {
        for (int i = 0; i < 500; i++) {
            Lawha l = (Lawha) SerializationUtils.clone(lawhat[randInt(0, 4)]);
            DateTime dt = Constants.DATE_FORMATTER.parseDateTime("2014-12-10 11:26:33.842-0500");
            dt = dt.plusDays(randInt(1, 20));
            dt = dt.plusHours(randInt(10, 200));
            dt = dt.plusMinutes(randInt(10, 50));
            l.setDateAdded(dt.toDate());
            System.out.println(l.getImgSrc() + " - " + Constants.DATE_FORMATTER.print(l.getDateAdded().getTime()));
            this.lawhaRepository.save(l);
        }
    }

    public void deleteAllData() {
        this.lawhaRepository.deleteAll();
    }

    private int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
