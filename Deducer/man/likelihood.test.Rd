\name{likelihood.test}
\alias{likelihood.test}
\title{
  Likelihood Ratio (G test) for contingency tables
}
\description{
  Performs a likelihood ratio test of independence 
}
\usage{
likelihood.test(x,y=NULL,conservative=FALSE)
}
\arguments{
  \item{x}{A vector or a matrix}
  \item{y}{A vector that is ignored if x is a matrix and required if x is a vector}
  \item{conservative}{If \code{TRUE}, the Williams' continuity correction is used}
}
\value{
A list with class "htest" containing the following components:
\item{statistic}{the value the chi-squared test statistic. }
\item{parameter}{
the degrees of freedom of the approximate chi-squared distribution of the test statistic.
}
\item{p.value}{the p-value for the test.}
\item{method}{a character string indicating the type of test performed, 
and whether the continuity correction was used.}
\item{data.name}{a character string giving the name(s) of the data.}
}
\author{
Pete Hurd and Ian Fellows
}

\seealso{
  \code{\link{chisq.test}}
}
\examples{
data(InsectSprays)
likelihood.test(InsectSprays$count>7,InsectSprays$spray)
}