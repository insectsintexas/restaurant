package com.example.bookingandlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    record FormItem(String id, String name, String number, String email, String date, String time, int people, String message) {
        public FormItem() {
            this(null, null, null, null, null, null, 0, null);
        }
    }
    private List<FormItem> formItems = new ArrayList<>();

    record ArticleItem(String id, String date, String title, String content, String url) {
        public ArticleItem() {
            this(null, null, null, null, null);
        }
    }
    private List<ArticleItem> articleItems = new ArrayList<>();

    private final ReservationDAO reservationDAO;
    private final ArticleDAO articleDAO;

    @Autowired
    HomeController(ReservationDAO reservationDAO, ArticleDAO articleDAO) {
        this.reservationDAO = reservationDAO;
        this.articleDAO = articleDAO;
    }

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/info")
    String info(Model model) {
        List<ArticleItem> articles = articleDAO.collectAll();
        model.addAttribute("articleList", articles);
        return "info";
    }


    @GetMapping("/form")
    public String fillInForm(Model model) {
        model.addAttribute("formItem", new FormItem());
        return "form";
    }

    @PostMapping("/form")
    public String submit(
            @RequestParam("name") String name,
            @RequestParam("number") String number,
            @RequestParam("email") String email,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("people") int people,
            @RequestParam("message") String message,
            Model model
    ) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        FormItem item = new FormItem(id, name, number, email, date, time, people, message);
        reservationDAO.add(item);

        model.addAttribute("formList", item);
        return "posted";
    }


    @GetMapping("/delete")
    String cancel(@RequestParam("id") String id) {
        reservationDAO.delete(id);
        return "redirect:/admin";
    }


    @RequestMapping("/access")
    String access() {
        return "access";
    }


    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/admin")
    String admin(Model model) {
        List<FormItem> formItems = reservationDAO.findAll();

        // 予約一覧を月ごとにグループ化
        Map<String, List<FormItem>> formItemsByMonth = formItems.stream()
                .collect(Collectors.groupingBy(item -> item.date().substring(0, 7)));

        model.addAttribute("formItemsByMonth", formItemsByMonth);
        return "admin";
    }

}
