
package main

/**
 * Created by FixError on 02.11.2016.
 */
import io.netty.handler.codec.http.HttpMethod
import org.wasabifx.wasabi.app.AppConfiguration
import org.wasabifx.wasabi.app.AppServer
import org.wasabifx.wasabi.interceptors.enableCORS
import org.wasabifx.wasabi.interceptors.serveStaticFilesFromFolder
import org.wasabifx.wasabi.interceptors.serveErrorsFromFolder
import org.wasabifx.wasabi.interceptors.serveFavIconAs
import org.wasabifx.wasabi.protocol.http.CORSEntry
import java.io.File

//java -cp web-restr-0.0.1-SNAPSHOT-bin.jar main.MainKt
fun main(args: Array<String>) {
    val headers = hashMapOf(
            "User-Agent" to "test-client",
            "Connection" to "close",
            "Cache-Control" to "max-age=0",
            "Accept" to "text/html,application/xhtml+xml;q=0.4,application/xml",
            "Accept-Encoding" to "gzip,deflate,sdch",
            "Accept-Language" to "en-US,en;q=0.8",
            "Accept-Charset" to "ISO-8859-1,utf-8;q=0.7,*"

    )

    val server = AppServer(AppConfiguration(port = 8080, enableLogging = true));

    server.serveFavIconAs(".${File.separatorChar}src${File.separatorChar}main${File.separatorChar}public${File.separatorChar}");
    server.serveStaticFilesFromFolder(".${File.separatorChar}src${File.separatorChar}main${File.separatorChar}public${File.separatorChar}");
    server.serveErrorsFromFolder(".${File.separatorChar}src${File.separatorChar}main${File.separatorChar}public${File.separatorChar}");

    print("${File.separatorChar}error.html")
    server.enableCORS(arrayListOf(
            CORSEntry(path = "/*",
                      origins ="*",
                      methods = CORSEntry.ALL_AVAILABLE_METHODS,
                      headers ="Origin, X-Requested-With, Content-Type, Accept",
                      credentials="",
                      preflightMaxAge=""),
            CORSEntry(path = "/account.*",
                      methods = setOf(HttpMethod.GET, HttpMethod.POST))
      )
    )

   /* server.get("/", {
             *//* val log = Log()
                log.info("URI requested is ${request.uri}")*//*
                print("log")
                next()
            }, {
                response.send("Hello World!", "application/json")
            }, {
                headers
            }
        )*/

    server.get("/", {
        response.send("Hello World!","text/html" )
    }
    )

    server.start()
}