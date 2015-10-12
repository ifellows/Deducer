\name{add.mantel.haenszel}
\alias{add.mantel.haenszel}
\title{
  Apply the Mantel-Haenszel test to a Contingency Table
}
\description{
  Applies and adds the Cochran-Mantel-Haenzsel test to a \code{contingency.tables} object. The Cochran-Mantel-Haenzsel tests 
  the independence of two nominal variables, stratified by a third nominal variable, assuming no three way interaction.
}
\usage{
add.mantel.haenszel(tables,conservative=FALSE)
}
\arguments{
  \item{tables}{An object of class \code{contingency.tables}}
  \item{conservative}{Should a continuity 'correction' be applied}
}
\details{
This is a convenience function wrapping \code{mantelhaen.test} in a \code{add.cross.strata.test} call.
See \code{\link{mantelhaen.test}} for further details.
}
\value{
  A \code{contingency.tables} object identical to \code{tables}, but with the test applied to each table. 
}
\seealso{
  \code{\link{add.cross.strata.test}}
  \code{\link{add.test}}
  \code{\link{mantelhaen.test}}
}
\examples{
dat<-data.frame(a=rnorm(100)>.5,b=rnorm(100)>0,c=rnorm(100)>(-.5))
tables1<-contingency.tables(
 row.vars=a,
 col.vars=b,
 stratum.var=c,data=dat)
tables1<-add.mantel.haenszel(tables1)
print(tables1,prop.r=TRUE,prop.c=TRUE,prop.t=FALSE)
} 