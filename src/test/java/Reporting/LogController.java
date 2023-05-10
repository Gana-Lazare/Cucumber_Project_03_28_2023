//package Reporting;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class LogController {
//    private static final Logger logger = LogManager.getLogger(LogController.class);
//    private static final String LOG_FILE = "logs/app.log";
//
//    @GetMapping("/logs")
//    public String logs(Model model) throws IOException {
//        Path path = Paths.get(LOG_FILE);
//        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
//        model.addAttribute("lines", lines);
//        logger.debug("Loaded {} lines from log file", lines.size());
//        return "logs";
//    }
//}
