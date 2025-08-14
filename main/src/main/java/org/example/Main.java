package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.template.Item;
import org.example.template.ItemRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.MediaType;
import java.util.List;
import java.util.Collections;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @RestController
    static class HelloController {
        private final JdbcTemplate jdbc;
        private final ItemRepository repo;

        HelloController(JdbcTemplate jdbc, ItemRepository repo) {
            this.jdbc = jdbc;
            this.repo = repo;
        }

        @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
        public String hello() {
            String currentDb = jdbc.queryForObject("SELECT DATABASE()", String.class);
            List<String> schemas = jdbc.queryForList(
                    "SELECT schema_name FROM information_schema.schemata ORDER BY schema_name",
                    String.class);
            List<String> tables = currentDb == null ? Collections.emptyList() :
                    jdbc.queryForList(
                            "SELECT table_name FROM information_schema.tables WHERE table_schema = ? AND table_type='BASE TABLE' ORDER BY table_name",
                            String.class, currentDb);

            StringBuilder sb = new StringBuilder();
            sb.append("<html><head><title>Status</title>");
            sb.append("<style>body{font-family:Segoe UI,Arial,sans-serif;margin:24px} h1{margin-bottom:8px} .cols{display:flex;gap:40px} ul{margin:8px 0;padding-left:18px}</style>");
            sb.append("</head><body>");
            sb.append("<h1>API OK - use /items (GET/POST)</h1>");
            sb.append("<p><b>Database:</b> ").append(currentDb == null ? "(none)" : currentDb).append("</p>");
                        sb.append("<div class='cols'>");
            sb.append("<div><h3>Schemas</h3><ul>");
            for (String s : schemas) sb.append("<li>").append(s).append("</li>");
            sb.append("</ul></div>");
            sb.append("<div><h3>Tables in ").append(currentDb == null ? "?" : currentDb).append("</h3><ul>");
            for (String t : tables) sb.append("<li>").append(t).append("</li>");
            sb.append("</ul></div>");
                        sb.append("</div>");

                                    // listar os itens do banco aqui
                                    List<Item> items = repo.findAll();
                                    sb.append("<h3>Items (" + items.size() + ")</h3><ul>");
                                    for (Item it : items) {
                                            sb.append("<li>")
                                                .append(it.getName()).append(" - ")
                                                .append(it.getDescription() == null ? "" : it.getDescription())
                                                .append("</li>");
                                    }
                        sb.append("</ul>");

                        sb.append("</body></html>");
            return sb.toString();
        }
    }

    @Bean
    CommandLineRunner initData(ItemRepository repo) {
        return args -> {
            // forçar substituição: limpa a tabela em toda inicialização (só dev)
            repo.deleteAll();
            repo.save(new Item("ola", "td bem"));
            repo.save(new Item("i hate", "popcorn"));
        };
    }
}