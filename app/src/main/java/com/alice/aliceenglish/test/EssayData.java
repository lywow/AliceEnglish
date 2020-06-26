package com.alice.aliceenglish.test;

import com.alice.aliceenglish.entity.Essay;

import java.util.ArrayList;
import java.util.List;

public class EssayData {
    public static List<Essay> getEssays(){
        List<Essay> list=new ArrayList<>();
        Essay e1=new Essay();
        e1.setTag("文化");
        e1.setId(10);
        e1.setTitle("Restoring Ancient Books, a Promising Task for China");
        e1.setContent("For 23 year-old Li Yanjun, it was a coincidence that she became an ancient book restorer. \n" +
                "When she was about to graduate from university, she watched the documentary \"Master in Forbidden City\" which depicts the lives of antique restorers, and decided to seek a similar job instead of becoming a teacher like most of her classmates. \n" +
                "She was lucky to be recruited to China's first and only museum on the restoration of ancient books in southwest China's Sichuan Province, which just opened at the end of last year. \n" +
                "The 1,100-square-meter museum in Chengdu displays more than 500 restored ancient texts, some of which are the works of accomplished restorers. It also is a gateway into the complicated procedures of the endangered craft that is vital to the country's huge inventory of ancient books. \n" +
                "To let visitors better understand the craft, which includes over 20 steps from making glue to bookbinding, the museum also has a demonstration zone. It is where Li Yanjun and her colleagues work. \n" +
                "\"I'm honored to do this job. I may not exist in this world 100 years later, but I would have left my trace in the restored ancient books. It is very meaningful to me,\" said Li. \n" +
                "The museum is part of the Ancient Texts Restoring Center of Western Sichuan, which is one of 26 national-level institutes focused on the craft inheritance. The center, founded in 2009, has developed from five restorers to more than 30, and restored over 170,000 ancient books at the end of 2019. \n" +
                "\"Many of the ancient books restored by us are very precious, such as the texts discovered from the Mogao Grottoes in 1900,\" said Peng Dequan, the founder of the museum. \n" +
                "The 74-year-old said many skilled restorers are elderly without successors, which prompted him to open the museum to promote the craft. \n" +
                "In China, books written or printed before 1912 featuring classical book-binding styles are classified as ancient books. \n" +
                "China started an ancient books protection project in 2007. It is estimated that China has about 50 million ancient books. \n" +
                "Incomplete statistics shows that about 30 percent of the ancient books need restoration, said Yang Guanghui, executive vice-president of Fudan University Chinese Ancient Books Preservation and Conservation Institute. \n" +
                "In the internet era, digitalization is also an important method to preserve the ancient books. \n" +
                "As of November 2019, the digital version of more than 72,000 ancient books has been published across the country, all of which are free for people to read online. ");
        e1.setDescribe("文化和自然遗产日，带你走近古籍修复");
        e1.setTime("2020-6-10");
        e1.setWordNumber(415);
        list.add(e1);

        Essay e2=new Essay();
        e2.setTag("科技");
        e2.setId(12);
        e2.setTitle("Tiny Human Livers Grown in Lab and Transplanted Into Rats");
        e2.setContent("Miniature human livers have been successfully grown in a lab by scientists – who then transplanted them into rats. \n" +
                "Human volunteers donated skin cells which researchers then developed into fully-functional mini-livers in a laboratory. \n" +
                "Researchers hope that their new technique might be a step towards one day replacing organ donation. \n" +
                "Study senior author Dr. Alejandro Soto-Gutierrez, at Pittsburgh University, said: “Seeing that little human organ there inside the animal – brown, looking like a liver – that was pretty cool. \n" +
                "“This thing that looks like a liver and functions like a liver came from somebody’s skin cells.” \n" +
                "The “made-to-order” livers secrete bile acids and urea just like their natural equivalents, the team explained. \n" +
                "The researchers created their mini livers by reprogramming human skin cells into different types of liver stem cells. \n" +
                "Then they seeded those human liver cells into a rat liver which had been stripped of its own cells. \n" +
                "As the ultimate test, the researchers transplanted their mini livers into five rats, which had been specially bred to resist organ rejection. \n" +
                "Four days after the transplant, researchers investigated how the implanted organs were faring. \n" +
                "Blood flow problems had developed within and around the graft, but the rats had human liver proteins in their blood serum proving that the transplants had worked. \n" +
                "Dr. Soto-Gutierrez, a pathology specialist, said: “The long-term goal is to create organs that can replace organ donation, but in the near future, I see this as a bridge to transplant. \n" +
                "“For instance, in acute liver failure, you might just need hepatic boost for a while instead of a whole new liver.” \n" +
                "But he conceded that there are significant challenges to overcome, including the organs’ long-term survival and several safety issues. ");
        e2.setDescribe("定制器官？皮肤细胞培育出微型人类肝脏");
        e2.setTime("2020-6-11");
        e2.setWordNumber(291);
        list.add(e2);
        return list;
    }
}
