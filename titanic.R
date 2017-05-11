
library('ggplot2') # visualization
library('ggthemes') # visualization
library('scales') # visualization
library('dplyr') # data manipulation
library('mice') # imputation
library('randomForest') # classification algorithm



    test<- read.csv("C:\\Users\\Benjamin\\Downloads\\test.csv",stringsAsFactors = F) 
    
    
  aaa <-test
  test$Title<- gsub('(.*,)|(\\..*)', '', test$Name)
 test$Name<-gsub("(,..\\."," )|(, ...\\."," )|(, ...\\."," )", test$Name)
  
      bbb<-test
    