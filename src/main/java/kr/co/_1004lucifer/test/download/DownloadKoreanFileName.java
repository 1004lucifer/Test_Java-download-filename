package kr.co._1004lucifer.test.download;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author 1004lucifer
 *
 * Test Date: 2019.10.23
 * Test Browser: IE11, Edge, Firefox(v70.0), Chrome(v77.0.3865.90)
 */
@RestController
public class DownloadKoreanFileName {

    @GetMapping("/downloadFile")
    public ResponseEntity<String> downloadFile(HttpServletRequest request) {

        String fileName = "abcd efg\t한글제목\t`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?.txt";
        String fileContent = "abcd efg\t한글내용\t`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";

//        IE, Edge 여부 확인
        String browser = request.getHeader("User-Agent");
        boolean isMs = browser.contains("MSIE") || browser.contains("Trident");

//        파일명 인코딩
        fileName = URLEncoder.encode(fileName).replaceAll("\\+", "%20");
//        Firefox 에서 사용할 Rfc5987
        String filenameRfc5987 = "UTF-8''" + fileName;

//        content-disposition 헤더 설정
//        Firefox에서 filename* 항목을 설정해줘야 한글이 깨지지 않는다.
//        MS에서 filename* 항목 설정 시 파일명이 정상적으로 안나오고 기본값으로 나온다.
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\";" +
                                (isMs ? "" : "filename*=\"" + filenameRfc5987 + "\";"))
                .body(fileContent);
    }
}
