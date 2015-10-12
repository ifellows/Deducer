\name{chi.noncentral.conf}
\alias{chi.noncentral.conf}
\title{
  Non-central Chi-Squared Confidence Interval
}
\description{
  Confidence interval for the Non-centrality parameter of Non-central chi-squared distribution
}
\usage{
chi.noncentral.conf(chival,df,conf,prec=.00001)
}
\arguments{
  \item{chival}{The observed Chi-Squared value}
  \item{conf}{The confidence level (e.g. .95)}
  \item{df}{Degrees of freedom}
  \item{prec}{Precision of estimate}

}
\value{
  A 2X2 matrix whose rows represent the upper and lower bounds, and whose columns represent the parameter value and upper tail percentiles.
}
\seealso{
  \code{\link{Chisquare}}
}
\references{
  Smithson, M.J. (2003). Confidence Intervals, Quantitative Applications in the Social Sciences Series, No. 140. Thousand Oaks, CA: Sage.
}
\examples{

chi.noncentral.conf(6,1,.95)
#            Result:

#      Non-Central          %
#Lower   0.2089385 0.97500899
#Upper  19.4443359 0.02499302
}  