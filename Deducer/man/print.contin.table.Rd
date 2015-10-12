\name{print.contin.table}
\alias{print.contin.table}
\title{Print method}
\description{
  Print object of class \code{"contin.table"} in nice layout.
}
\usage{
\method{print}{contin.table}(
	x,digits=3,prop.r=TRUE,prop.c=TRUE,prop.t=TRUE,
	expected.n=FALSE,residuals=FALSE,std.residuals=FALSE,
	adj.residuals=FALSE,no.tables=FALSE,...)
}
\arguments{
  \item{x}{Object of class \code{"contin.table"}.}
  \item{digits}{Number of digits to round to.}
  \item{prop.r}{Logical. print row proportions.}
  \item{prop.c}{Logical. print column proportions.}
  \item{prop.t}{Logical. print proportions.}
  \item{expected.n}{Logical print expected cell counts.}
  \item{residuals}{Logical. print residuals.}
  \item{std.residuals}{Logical. print standardized residuals.}
  \item{adj.residuals}{Logical. Print Adjusted residuals}
  \item{no.tables}{Logical. Suppress tables}
  \item{\dots}{further arguments}  
}
\author{
Ian Fellows based on the CrossTable function from the gmodels package maintained by Gregory R. Warnes
}
\value{
  none
} 