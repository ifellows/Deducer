\name{extract.counts}
\alias{extract.counts}
\title{
  Extract Contingency Table Arrays
}
\description{
  Extracts the counts of a contingency.tables object
}
\usage{
extract.counts(tables)
}
\arguments{
  \item{tables}{A \code{contingency.table} object}
}
\value{
 A named list of three dimensional arrays. One for each \code{contin.table} in \code{tables}
 }
\seealso{
  \code{\link{contingency.tables}}
}
\examples{
temp.data<-data.frame(a=rnorm(100)>0,b=rnorm(100)>0,gender=rep(c("male","female"),50))
#a vs. b stratified by gender
tab<-contingency.tables(a,b,gender,data=temp.data)
tab

##extract counts
extract.counts(tab)

##Yields something like the following:
#$`a by b`
#, , female
#
#      FALSE TRUE
#FALSE    11    9
#TRUE     15   15
#
#, , male
#
#      FALSE TRUE
#FALSE    10   10
#TRUE     22    8

} 