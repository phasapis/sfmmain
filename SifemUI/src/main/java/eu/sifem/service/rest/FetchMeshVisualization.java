package eu.sifem.service.rest;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by nikos on 26/11/2015.
 */
@RestController
@RequestMapping("/fetchMeshVisualization")
public class FetchMeshVisualization {
    public final String modelPath = "/home/panos/Downloads/Sifem_code_18062015/Sifem_code_18062015/Sifem/SifemUI/src/main/webapp/faces/resources/stl/";

    @RequestMapping(value="/typeForModel/{model}", method = RequestMethod.GET)
    public String getType(@PathVariable String model, HttpServletResponse response) {
        File f = new File(modelPath+model+".stl");
        if(f.exists() && !f.isDirectory()) {
            return "stl";
        }
        f = new File(modelPath+model+".png");
        if(f.exists() && !f.isDirectory()) {
            return "png";
        }
        return "";
    }

    @RequestMapping(value="/stlForModel/{model}", method = RequestMethod.GET)
    public void getSTL(@PathVariable String model, HttpServletResponse response) {
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(new File(modelPath+model+".stl"));
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/pngForModel/{model}", method = RequestMethod.GET)
    public void getPNG(@PathVariable String model, HttpServletResponse response) {
        try {
            response.setContentType("image/png");
            // get your file as InputStream
            InputStream is = new FileInputStream(new File(modelPath+model+".png"));
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
