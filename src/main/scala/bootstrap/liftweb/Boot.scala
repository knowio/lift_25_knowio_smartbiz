package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._


/** A class that's instantiated early and run.  It allows the application to
 * modify lift's environment
 */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu
        Menu("Features") / "about" submenus(
        Menu.i("About") / "about" / "about",
        Menu.i("Services") / "about" / "services",
        Menu.i("FAQ") / "about" / "faq",
        Menu.i("Pricing") / "about" / "pricing",
        Menu.i("Testimonials") / "about" / "testimonials",
        Menu.i("404") / "about" / "404",
        Menu.i("Single Post") / "about" / "post",
        Menu.i("Right Sidebar") / "about" / "sidebar-right",
        Menu.i("Left Sidebar") / "about" / "sidebar-left",
        Menu.i("Full Width") / "about" / "fullwidth" ),
      Menu.i("Blog") / "blog",
      Menu("Portfolio") / "portfolio" submenus(
        Menu.i("Three Columns") / "portfolio" / "portfolio-3col",
        Menu.i("Four Columns") / "portfolio" / "portfolio-4col",
        Menu.i("Featured") / "portfolio" / "featured",
        Menu.i("Single") / "portfolio" / "single"),
      Menu.i("Contact") / "contact" ,

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
        "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery172
    JQueryModule.init()
  }
}
