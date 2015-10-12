\name{deducer.addMenu}
\alias{deducer.addMenu}
\alias{deducer.addMenuItem}
\alias{deducer.getMenus}
\alias{deducer.setMenus}
\alias{menuFunctions}
\title{
  Controls Deducer's command line menus
}
\description{
Controls Deducer's command line menus
}
\usage{
deducer.addMenu(name, pos=length(menus)+1)
deducer.setMenus(newMenus)
deducer.getMenus()
deducer.addMenuItem(name, pos=NULL, command, menuName, silent=TRUE)
menuFunctions()
}
\arguments{
  \item{name}{name of item or menu to add}
  \item{pos}{position at which to add the item or menu}
  \item{menuName}{the name of the menu to add the item to}
  \item{command}{A character vector representing the R command to be run}
  \item{silent}{Should the command be executed silently}
  \item{newMenus}{new menus}

  
}
\examples{

#add a menu with two items
deducer.addMenu("TestMenu")
deducer.addMenuItem("test1",,"cat('test1 selected')","TestMenu")
deducer.addMenuItem("test2",,"print(summary(lm(rnorm(100)~rnorm(100))))","TestMenu")

#Add menu to gui if applicable
if(.windowsGUI){
	winMenuAdd("TestMenu")
	winMenuAddItem("TestMenu", "test1", "cat('test1 selected')")
	winMenuAddItem("TestMenu", "test2", "print(summary(lm(rnorm(100)~rnorm(100))))")
}else if(.jgr){
	jgr.addMenu("TestMenu")
	jgr.addMenuItem("TestMenu", "test1", "cat('test1 selected')")
	jgr.addMenuItem("TestMenu", "test2", "print(summary(lm(rnorm(100)~rnorm(100))))")	
}


}