\name{add.cross.strata.test}
\alias{add.cross.strata.test}
\title{
  Apply a Stratified test to a Contingency Table
}
\description{
  Applies and adds a hypothesis test to a \code{contingency.tables} object.
}
\usage{
add.cross.strata.test(tables,name,htests,types=c("asymptotic","monte.carlo","exact"))
}
\arguments{
  \item{tables}{An object of class \code{contingency.tables}}
  \item{name}{The name of the hypothesis test}
  \item{htests}{A function or list of functions which take a three dimensional array as it's argument
  				and returns an object of class \code{htest} }
  \item{types}{A character vector with the same number of items as htests, indicating what type of test
  				was done}
}
\value{
  A \code{contingency.tables} object identical to \code{tables}, but with the test applied to each table. 
}
\seealso{
  \code{\link{add.mantel.haenszel}}
  \code{\link{add.test}}
}
\examples{
dat<-data.frame(a=rnorm(100)>.5,b=rnorm(100)>0,c=rnorm(100)>(-.5))
tables<-contingency.tables(
 row.vars=a,
 col.vars=b,
 stratum.var=c,data=dat)
add.cross.strata.test(tables,"Mantel-Haenszel",list(function(x) mantelhaen.test(x,correct=FALSE)),
 "asymptotic")
tables
}