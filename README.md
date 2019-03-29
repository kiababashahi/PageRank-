 Imagine you are surfing the web by randomly clicking on the links provided in one web-page to another. An interesting question would be: Is there any way to estimate which page you will eventually end up on? 

A page is considered popular if a lot of pages are referring to it. It is also reasonable to assume that if there is a link from a popular page to a another page then the referred page could also become popular. 

Can we predict which page we will end up on? The first answer could be: Most probably in one of the popular pages. But what if there is a loop between the pages, e.g., A->B->A ? Or what if we end up in a page that does not have any links to other pages, i.e., the page is a sink?  
 
The Page Rank algorithm generates a probability distribution describing how likely a person randomly clicking on links on some websites will end up at any particular page. It works based on the fact that the page rank of a page depends on the page rank of the pages pointing to it and is computed via an iterative algorithm using the formula:
 
PR(A) = (1-d) + d (PR(P1)/out(P1) + … + PR(Pn)/out(Pn)) 

 In this formula P1,P2,...,Pn are the pages pointing to page A. PR(P) indicates the page rank at a given node P and out(P) is the out degree of that node. Indeed, the more links provided on a certain web-page to other pages, the less likely one would end up in a particular one of them. d is called the damping factor. It is used to avoid a very popular page to get all of the attention and would try to balance the attention.

I will present the network as a digraph for which each web-page is represented as a node and if page a points to page b there will be an edge between them. In my implementation, if website a points to website b, I store their URLs in an array called URLS[2]. I will store the entire set of links in an arraylist called links and will eventually build the network using the links, a hashmap data structure and my node class. 

I will first initialize my network by creating the nodes and connecting them using the information provided in the Links array_list. 
```ruby
public void Creat_Network() {
		for (int i = 0; i < links.size(); i++) {
			Node u = new Node(links.get(i)[0]);
			Node v = new Node(links.get(i)[1]);
			Web_sites.putIfAbsent(links.get(i)[0], u);
			Web_sites.putIfAbsent(links.get(i)[1], v);
		}
		for (int i = 0; i < links.size(); i++) {
			creat_edge(Web_sites.get(links.get(i)[0]), Web_sites.get(links.get(i)[1]));
		}
		initialize();
	}
```
For the initialization step, I initialized all the pages to have the same initial page rank by setting their value equal to 1/(number_of_web_pages) using the initialize function below: 
```ruby
	private void initialize() {
		for (Map.Entry<String, Node> entry : Web_sites.entrySet()) {
			entry.getValue().set_PR(1.0 / Web_sites.size());
		}
	}

```
 

I also kept track of the sinks and sat the value of the damping factor at the beginning as both of these parameters will play a huge role in the design of the page rank algorithm. (Usually the damping factor is sat to 0.85 but I have also tried other values). 

As mentioned before, The page rank algorithm is an iterative algorithm. The update in the value of the page rank of each node in the network will continue until either the change in the overall page rank is less than epsilon (e) or we have reached a certain number of iterations. The following block of code computes the page rank of each of the nodes in the network:

```ruby
public void calculate_PR(double e,int max, double d) {
		int count=0;
		double sum=0.0;
		double oldRank=0;
		double NewRank=0.0;
		double SumofNewRank=Double.MAX_VALUE;
		for(Map.Entry<String, Node> entry: Web_sites.entrySet()) {
			oldRank+=entry.getValue().Get_PR();
		}
		double change=SumofNewRank-oldRank;
		while( count<max && change > e) {
			//SumofNewRank=0;
			for(Map.Entry<String, Node> entry:Web_sites.entrySet()) {
				sum=0;
				 for(int i=0;i<entry.getValue().Get_in().size();i++) {
					 sum+=entry.getValue().Get_in().get(i).Get_Ratio();
				 }
				 NewRank=(1-d)+ (d*sum);
				 entry.getValue().set_PR(NewRank);
			}
			count++;
			for(Map.Entry<String, Node> entry:Sinks.entrySet()) {
				for(Map.Entry<String, Node> Webs:Web_sites.entrySet()) {
					if(!(entry.getKey().equals(Webs.getKey()))){
						double temp=Webs.getValue().Get_PR();
						temp+=(1.0/(Web_sites.size()-1))*entry.getValue().Get_PR();
						NewRank=temp;
						Webs.getValue().set_PR(NewRank);
					}
				}
			}
			SumofNewRank=0;
			for(Map.Entry<String, Node> entry: Web_sites.entrySet()) {
				SumofNewRank+=entry.getValue().Get_PR();
			}
			change=SumofNewRank-oldRank;
			oldRank=SumofNewRank;
		}
	}
```
The effect of different parameters and the information on the data sets I used can be accessed in the pdf file provided in the repository.  
