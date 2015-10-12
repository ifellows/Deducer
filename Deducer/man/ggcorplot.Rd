\name{ggcorplot}
\alias{ggcorplot}
\title{
  Correlation matrix
}
\author{
Mike Lawrence and Ian Fellows
}
\description{
  Plots a correlation matrix
}
\usage{
ggcorplot(cor.mat,data=NULL,lines=TRUE,line.method=c("lm","loess"),type="points",
		alpha=.25,main="auto",var_text_size=5,
		cor_text_limits=c(5,25),level=.05)
}
\arguments{
  \item{cor.mat}{a \code{cor.matrix} object to plot}
  \item{data}{the data.frame used to compute the correlation matrix}
  \item{lines}{Logical. Should regression lines be drawn.}
  \item{type}{type of plot. "points" or "bins"}
  \item{line.method}{Character. Type of regression line.}
  \item{alpha}{numeric. level of alpha transparency for the points.}
  \item{main}{Title of the plot. defaults to the method of cor.mat.}
  \item{var_text_size}{size of the diagonal variable names.}
  \item{cor_text_limits}{lower and upper bounds for the size of the correlation text.}
  \item{level}{the size of the test differentiated by text color.}

}
\seealso{
  \code{\link{cor.matrix}}
  \code{\link{qscatter_array}}
}
\examples{
data(mtcars)
corr.mat1<-cor.matrix(variables=d(mpg,carb,carb+rnorm(length(carb))),,
	 data=mtcars,
	 test=cor.test,
	 method='spearman',
	alternative="two.sided",exact=FALSE)
	
p<-ggcorplot(corr.mat1,data = mtcars)
print(p)
\dontrun{

has.hex<-require("hexbin")
if(has.hex){
	data(diamonds)
	corr.mat<-cor.matrix(variables=d(price,carat,color),,
		 data=diamonds,
		 test=cor.test,
		 method='spearman',
		alternative="two.sided")

	p1 <- ggcorplot(cor.mat=corr.mat,data=diamonds,type="bins",
		cor_text_limits=c(5,15),
		lines=FALSE)
	print(p1)
	rm('corr.mat')

}
}

}
