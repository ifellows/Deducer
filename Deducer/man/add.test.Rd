\name{add.test}
\alias{add.test}
\alias{add.chi.squared}
\alias{add.likelihood.ratio}
\alias{add.fishers.exact}
\alias{add.correlation}
\alias{add.kruskal}
\title{
  Apply a test to a Contingency Tables object
}
\description{
  Applies and adds a test to a \code{contingency.tables} object. 
}
\usage{
add.test(tables,name,htests,types=c("asymptotic","monte.carlo","exact"))

add.chi.squared(tables, simulate.p.value = FALSE, B = 10000)

add.likelihood.ratio(tables, conservative = FALSE, simulate.p.value = FALSE, B = 10000)

add.fishers.exact(tables, simulate.p.value = FALSE, B = 10000)

add.correlation(tables,method=c("spearman","kendall"))

add.kruskal(tables,nominal=c("both","rows","cols"))
}
\arguments{
  \item{tables}{An object of class \code{contingency.tables}}
  \item{name}{Name of the test}
  \item{htests}{A function or list of functions which take a matrix as it's argument
  				and returns an object of class \code{htest} }
  \item{types}{A character vector with the same number of items as \code{htests}, indicating what type of test
  				was done}
  \item{conservative}{Should a conservative p-value be computed. i.e. One with a continuity correction for
  				asymptotic tests and not using the mid p-value for exact and approximate tests }
  \item{simulate.p.value}{If \code{TRUE} p-values will be computed via monte carlo simulation}
  \item{B}{the number of samples for the monte carlo simulation}
  \item{method}{the type of correlation}
  \item{nominal}{Should the rows or columns be considered nominal.}
  
}
\details{
add.test applies a supplied list of tests to all of the tables in \code{tables}. 

add.chi.squared is a wrapper function applying the \code{\link{chisq.test}} function to each table.
add.likelihood.ratio is a wrapper function applying the \code{\link{likelihood.test}} function to each table.
add.fishers.exact is a wrapper function applying the \code{\link{fisher.test}} function to each table.
add.correlation is a wrapper function applying the \code{\link{cor.test}} function to each table.
add.kruskal is a wrapper function applying the \code{\link{kruskal.test}} function to each table.

}
\value{
  A \code{contingency.tables} object identical to \code{tables}, but with the test applied to each table. 
}
\seealso{
  \code{\link{add.cross.strata.test}}
  \code{\link{likelihood.test}}
  \code{\link{cor.test}}
  \code{\link{kruskal.test}} 
}
\examples{
dat<-data.frame(a=rnorm(100)>.5,b=rnorm(100)>0,c=rnorm(100)>(-.5))
tables<-contingency.tables(
	row.vars=a,
	col.vars=b,
	stratum.var=c,data=dat)
tables<-add.chi.squared(tables,simulate.p.value=TRUE,B=10000)
tables<-add.likelihood.ratio(tables)
tables<-add.fishers.exact(tables)
tables<-add.correlation(tables,method='kendall')
tables<-add.kruskal(tables)
tables<-add.mantel.haenszel(tables)
print(tables)
remove(tables)
}  