# Deducer
An intuitive, cross-platform graphical data analysis system. It uses menus and dialogs to guide the user efficiently through the data manipulation and analysis process, and has an excel like spreadsheet for easy data frame visualization and editing. Deducer works best when used with the Java based R GUI JGR, but the dialogs can be called from the command line. Dialogs have also been integrated into the Windows Rgui.

# Usage

See: http://www.deducer.org


# Building and installing
Get the released version from CRAN:

```R
install.packages("Deducer")
library(JGR)
launchJGR(jgrArgs="--withPackages=Deducer")
```

To build from this repository

```
sh mkdist
R CMD INSTALL Deducer_*.*.tar.gz
```
