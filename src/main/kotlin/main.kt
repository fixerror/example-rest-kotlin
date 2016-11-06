
package main

/**
 * Created by FixError on 02.11.2016.
 */
import org.slf4j.LoggerFactory
import io.netty.handler.codec.http.HttpMethod
import org.wasabifx.wasabi.app.AppConfiguration
import org.wasabifx.wasabi.app.AppServer
import org.wasabifx.wasabi.interceptors.*
import org.wasabifx.wasabi.protocol.http.CORSEntry
import java.io.File

//java -cp web-restr-0.0.1-SNAPSHOT-bin.jar main.MainKt
fun main(args: Array<String>) {
    val Log = LoggerFactory.getLogger("MainKt.class")
    val server = AppServer(AppConfiguration(port = 8080, enableLogging = true));

    val settingPath = ".${File.separatorChar}src${File.separatorChar}main${File.separatorChar}public${File.separatorChar}"

    server.serveFavIconAs(settingPath + "favicon.ico");
    server.serveStaticFilesFromFolder(settingPath);
    server.serveErrorsFromFolder(settingPath);
    server.logRequests()

    //methods = setOf(HttpMethod.GET, HttpMethod.POST)
    server.enableCORS(arrayListOf(
            CORSEntry(path = "/*",
                      origins ="*",
                      methods = setOf(HttpMethod.GET, HttpMethod.POST),
                      headers ="Origin, X-Requested-With, Content-Type, Accept",
                      credentials="",
                      preflightMaxAge=""),
            CORSEntry(path = "/account.*",
                      methods = setOf(HttpMethod.GET, HttpMethod.POST))
      )
    )

    server.get("/", {
             Log.info("URI requested is ${request.uri}")
             next()
            }, {
                response.statusCode =200;
                response.send("Hello World!", "application/json")
            }
        )


    server.start()
}