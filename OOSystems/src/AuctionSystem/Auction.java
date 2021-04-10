package AuctionSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDate;

public final class Auction implements Serializable {



	private Seller seller;
	private Buyer auctionWinner;
	private Item item;
	private double startPrice, reservePrice;
	private LocalDateTime closeDate;
	private Status status;
	private List<Bid> bids = new ArrayList<Bid>();

	public Auction(Seller seller, Item item, double startPrice, double reservePrice, LocalDateTime closeDate) throws Exception{
		this.seller = seller;
		this.item = item;
		this.startPrice = startPrice;
		this.reservePrice = reservePrice;
		
		//Need to make sure that the close date is within 7 days and not in the past
		//checks whether or not the date is within 7 days
		if (this.closeDateValidation(closeDate)) {
		this.closeDate = closeDate;
	} else {
		throw new Exception("Close date should be within 7 days and not in the past!");
	}
		this.status = Status.PENDING;
	}
	
	public String toString() {
		String theString = ("Item Name: "+this.item.getName()+" Start Price: "+this.startPrice+" Reserve Price: "+this.reservePrice+" Closing Date: "+this.closeDate);
		return theString;
	}

	//Get Functions
	public Seller getSeller(){
		return this.seller;
	}
	
	public Buyer getAuctionWinner() {
		return this.auctionWinner;
	}

	public Item getItem(){
		return this.item;
	}
	
	public double getStartPrice() {
		return this.startPrice;
	}
	public double getReservePrice() {
		return this.reservePrice;
	}
	
	public LocalDateTime getCloseDate() {
		return this.closeDate;
	}
	
	//verifying the dates that they are within 7 days
	public boolean closeDateValidation(LocalDateTime inputDate) {
		if(inputDate.isAfter(LocalDateTime.now()) && inputDate.isBefore(LocalDateTime.now().plusDays(7))) {
			return true;
		} else {
			return false;
		}
	}

	public void placeBid(double amount, Buyer buyer) throws Exception{
		if (getStatus().equals(Status.ACTIVE)) {
			bids.add(new Bid(amount, buyer, LocalDateTime.now()));
		} else {
			throw new Exception("The Auction is not active.");
		}
	}

	public List<Bid> getBids(){
		return this.bids;
	}

	public void verify() {
		this.status = Status.ACTIVE;
	}

	//need to add bid winner to the close function
	public void close() {
		this.status = Status.CLOSED;
	}
	
	public Status getStatus() {
		return this.status;
	}

	public boolean isBlocked() {
		if(this.status.equals(Status.BLOCKED)) {
			return true;
		} else {
			return false;
		}
	}

	public void setBlocked() {
		this.status = Status.BLOCKED;
	}
	
	public void setUnblock() {
		this.status = Status.ACTIVE;
	}

}
