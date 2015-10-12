\name{descriptive.table}
\alias{descriptive.table}
\title{
  Table of Descriptives
}
\description{
    Table of descriptive statistics, possibly stratified
}
\usage{
descriptive.table(vars,
	strata,
	data,
	func.names = c("Mean","St. Deviation","Median",
		"25th Percentile","75th Percentile",
		"Minimum","Maximum","Skew","Kurtosis","Valid N"),
	func.additional)
}
\arguments{
  \item{vars}{A variable or data.frame containing variables on which to run descriptive statistics.}
  \item{data}{The data frame in which vars is evaluated}
  \item{strata}{A variable or data.frame containing variables on which to stratify}
  \item{func.names}{A character vector of built-in statistics}
  \item{func.additional}{A named list of functions. Each function should take a numeric vector as its argument, and return a single value}
}
\value{
  Returns a list of \code{matrix} objects containing descriptive information on all variables in \code{dat}. 
  One for each level or combination of levels in \code{strata}.
}
\seealso{
  \code{\link{frequencies}}
  \code{\link{mean}}
  \code{\link{by}}
}
\examples{
data(mtcars)
##means and standard deviations
descriptive.table(vars = d(mpg,hp),data= mtcars,
	func.names =c("Mean","St. Deviation","Valid N"))
##stratifying by cyl
descriptive.table(vars = d(mpg,hp) ,
	strata = d(cyl),data= mtcars,
	func.names =c("Mean","St. Deviation","Valid N"))
	
	
func.list=list(mean.deviance=function(x) mean(abs(x-mean(x))))

##Adding deviance as a statistic
descriptive.table(vars = d(mpg,hp) ,
	strata = d(cyl),data= mtcars,
	func.names =c("Mean","St. Deviation","Valid N"),func.additional=func.list)
}