
package main

/**
 * Created by FixError on 02.11.2016.
 */
import org.wasabifx.wasabi.app.AppConfiguration
import org.wasabifx.wasabi.app.AppServer
import models.Test1
import models.Test2


fun main(args: Array<String>){
    val t = Test1()
    t.test1()
    Test2.test();

    val server = AppServer(AppConfiguration(enableLogging = false))

    server.get("/", {
        response.send("Hello World!")
    })

    server.start()
}