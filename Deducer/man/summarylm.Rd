
\name{summarylm}
\alias{summarylm}
\title{
  Summary table for a linear model
}
\description{
  Computes the coefficients, std. errors, t values, and p-values for a linear model in the presence of possible heteroskedasticity.
}
\usage{
summarylm(object,correlation=FALSE,symbolic.cor = FALSE,white.adjust=FALSE,...)
}
\arguments{
  \item{object}{an object of class lm.}
  \item{correlation}{a logical value indicating whether parameter correlations should be printed.}
  \item{symbolic.cor}{logical. If TRUE, print the correlations in a symbolic form (see symnum) rather than as numbers. Effective only
  						if white.adjust is FALSE.}
  \item{white.adjust}{value passed to \code{hccm} indicating the type of robust adjustment to be used. If TRUE, type is assumed to be 'hc3'}
  \item{...}{additional parameters passed to stats::summary.lm}
}
\details{
If white.adjust is false, the function returns a value identical to stats::summary.lm. Otherwise, robust summaries are computed
}
\value{
  A summary table
}
\examples{
ctl <- c(4.17,5.58,5.18,6.11,4.50,4.61,5.17,4.53,5.33,5.14)
trt <- c(4.81,4.17,4.41,3.59,5.87,3.83,6.03,4.89,4.32,4.69)
group <- gl(2,10,20, labels=c("Ctl","Trt"))
weight <- c((ctl-mean(ctl))*10+mean(ctl), trt)
lm.D9 <- lm(weight ~ group)
summarylm(lm.D9,white.adjust=TRUE)
}
