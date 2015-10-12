\name{oneway.plot}
\alias{oneway.plot}
\title{One Way PLot}
\description{
  plots a categorical variable against a series of continuous variables
}
\usage{
oneway.plot(formula,data=NULL,alpha=.2,
		box=TRUE,points=TRUE,scale=FALSE)
}
\arguments{
 \item{formula}{A formula, the left hand side of which indicated the outcomes, and the right hand side of which contains the factor}
  \item{data}{A data.frame}
  \item{alpha}{alpha transparency level for the points.}
  \item{box}{prints boxplot}
  \item{points}{prints jitter plot}
  \item{scale}{standardize the variables prior to plotting}
}
\value{
  a ggplot object
} 
\examples{

oneway.plot(d(DriversKilled, drivers, front, rear,   kms, PetrolPrice)~law,as.data.frame(Seatbelts))

} 