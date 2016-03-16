package com.ems.espupdate.service;

import com.ems.espupdate.persistence.entity.ESPData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sbn on 16.03.2016.
 */
@WebServlet(name = "ESPUpdateServlet", urlPatterns = ("/update"))
public class ESPUpdateServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ESPUpdateServlet.class);

    @EJB
    private PersistenceService ps;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String useragent = request.getHeader("HTTP_USER_AGENT");
        String staMac = request.getHeader("HTTP_X_ESP8266_STA_MAC");
        String apMac = request.getHeader("HTTP_X_ESP8266_AP_MAC");
        String freeSpace = request.getHeader("HTTP_X_ESP8266_FREE_SPACE");
        String sketchSize = request.getHeader("HTTP_X_ESP8266_SKETCH_SIZE");
        String chipSize = request.getHeader("HTTP_X_ESP8266_CHIP_SIZE");
        String sdkVersion = request.getHeader("HTTP_X_ESP8266_SDK_VERSION");
        String version = request.getHeader("HTTP_X_ESP8266_VERSION");
        if (useragent == null || !"ESP8266-http-Update".equals(useragent)) {
            logger.warn("wrong useragent detected: " + useragent);
            response.sendError(400);
            return;
        }
        if (!StringUtils.isNotEmpty(staMac) || !StringUtils.isNotEmpty(apMac) || !StringUtils.isNotEmpty(freeSpace) ||
                !StringUtils.isNotEmpty(sketchSize) || !StringUtils.isNotEmpty(chipSize) || !StringUtils.isNotEmpty(sdkVersion)
                || !StringUtils.isNotEmpty(version)) {
            logger.warn("missing headerinfos, staMac:" + staMac + " apMac:" + apMac + " freeSpace:" + freeSpace + " sketchSize:" +
                    sketchSize + " chipSize:" + chipSize + " sdkVersion:" + sdkVersion + " version:" + version);
            response.sendError(400);
            return;
        }

        ESPData data = ps.findDataByMac(staMac);
        if (data == null) {
            data = new ESPData();
            data.setApMacAddress(apMac);
            data.setMacAddress(staMac);
            data.setChipSize(chipSize);
            data.setFreeSpace(freeSpace);
            data.setSdkVersion(sdkVersion);
            data.setVersion(version);
            ps.persist(data);
            logger.warn("found no ESP Data, must be a new ESP device, storing into DB. "+data);
            response.sendError(400);
            return;
        }
        String fileName = data.getNewVersion();
        File file = new File(System.getProperty("ems.apps.datadir") + "/espupdate/" + data.getId() +"/"+ fileName);
        if (!file.exists()) {
            response.sendError(500);
            return;
        }
        try (InputStream fis = new FileInputStream(file);
             ServletOutputStream os = response.getOutputStream();) {
            response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setHeader("x-MD5", data.getMd5NewVersion());
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
        }

    }

}
