\name{contingency.tables}
\alias{contingency.tables}
\title{
  Contingency Tables
}
\description{
  Creates a contingency.tables object
}
\usage{
contingency.tables(row.vars, col.vars, stratum.var, data=NULL, missing.include=FALSE )
}
\arguments{
  \item{row.vars}{A variable or data frame evaluated in data}
  \item{col.vars}{A variable or data frame evaluated in data}
  \item{stratum.var}{A variable evaluated in data}
  \item{data}{A \code{data.frame}}
  \item{missing.include}{A logical indicating whether a missing category should be included in the table}
}
\value{
 A list with class "contingency.tables." Each element of the list is a single contingency table of 
 class "contin.table" corresponding to each combination of elements of \code{row.vars} and \code{col.vars} 
 stratified by \code{stratum.var}
 }
\seealso{
  \code{\link{extract.counts}}
}
\examples{
temp.data<-data.frame(a=rnorm(100)>0,b=rnorm(100)>0,gender=rep(c("male","female"),50))
#a vs. b stratified by gender
tab<-contingency.tables(a,b,gender,data=temp.data)
tab

##add in chi-squared tests
tab<-add.chi.squared(tab)
tab
} 