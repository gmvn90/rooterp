package com.swcommodities.wsmill.hibernate.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dunguyen on 10/12/16.
 */
public class InstructionStatus {
	private static Byte defaultStatusNumber = Byte.valueOf("0");
	
	public static class InstructionRequestStatus {
		public static Byte PENDING = Byte.valueOf("0");
		public static Byte APPROVED = Byte.valueOf("1");
	}
	
	public static class InstructionCompletionStatus {
		public static Byte PENDING = Byte.valueOf("0");
		public static Byte COMPLETED = Byte.valueOf("1");
	}

	public static class InvoiceCompletionStatus {
		public static Integer PENDING = 0;
		public static Integer COMPLETED = 1;
	}
    
    public static class FinanceCompletionStatus {
		public static Integer PENDING = 0;
		public static Integer COMPLETED = 1;
	}
	
	public static class SampleSentApprovalStatus {
		public static Byte PENDING = Byte.valueOf("0");
		public static Byte APPROVED = Byte.valueOf("1");
		public static Byte REJECTED = Byte.valueOf("2");
	}

	public static class TransactionApprovalStatus {
		public static Integer PENDING = 0;
		public static Integer APPROVED = 1;
		public static Integer REJECTED = 2;
	}

	public static class TransactionType {
		public static Integer DI = 1;
		public static Integer PI = 2;
		public static Integer SI = 3;
	}

	public static class SampleSentType {
		public static Integer PSS = 1;
		public static Integer TYPE = 2;
	}

	public static class TransactionInvoicedStatus {
		public static Integer PENDING = 0;
		public static Integer INVOICED = 1;
	}
	
	public static class SampleSentSendingStatus {
		public static Byte PENDING = Byte.valueOf("0");
		public static Byte SENT = Byte.valueOf("1");
	}
    
    public static class ShipmentStatus {
        public static Integer PENDING = 0;
		public static Integer COMPLETED = 1;
    }
	
	
	// instruction
    public static Map<Byte, String> getRequestStatuses() {
        Map<Byte, String> map = new HashMap<>();
        map.put(InstructionRequestStatus.PENDING, "Pending");
        map.put(InstructionRequestStatus.APPROVED, "Approved");
        return map;
    }

    // instruction
    public static Map<Byte, String> getCompletionStatuses() {
        Map<Byte, String> map = new HashMap<>();
        map.put(InstructionCompletionStatus.PENDING, "Pending");
        map.put(InstructionCompletionStatus.COMPLETED, "Completed");
        return map;
    }

	public static Map<Integer, String> getInvoiceCompletionStatuses() {
		Map<Integer, String> map = new HashMap<>();
		map.put(InvoiceCompletionStatus.PENDING, "Pending");
		map.put(InvoiceCompletionStatus.COMPLETED, "Completed");
		return map;
	}

    public static Map<Byte, String> getContainerStatuses() {
        Map<Byte, String> map = new HashMap<>();
        map.put(Byte.valueOf("1"), "FCL/FCL");
        map.put(Byte.valueOf("2"), "LCL/FCL");
        return map;
    }
    
    // sample sent
    public static Map<Byte, String> getSendingStatuses() {
        Map<Byte, String> map = new HashMap<>();
        map.put(SampleSentSendingStatus.PENDING, "Pending");
        map.put(SampleSentSendingStatus.SENT, "Sent");
        return map;
    }

    public static Map<Byte, String> getApprovalStatuses() {
        Map<Byte, String> map = new HashMap<>();
        map.put(SampleSentApprovalStatus.PENDING, "Pending");
        map.put(SampleSentApprovalStatus.APPROVED, "Approved");
        map.put(SampleSentApprovalStatus.REJECTED, "Rejected");
        return map;
    }

	public static Map<Integer, String> getApprovalStatusesInteger() {
		Map<Integer, String> map = new HashMap<>();
		map.put(TransactionApprovalStatus.PENDING, "Pending");
		map.put(TransactionApprovalStatus.APPROVED, "Approved");
		map.put(TransactionApprovalStatus.REJECTED, "Rejected");
		return map;
	}

	public static Map<Integer, String> getInvoicedStatus() {
		Map<Integer, String> map = new HashMap<>();
		map.put(TransactionInvoicedStatus.PENDING, "Pending");
		map.put(TransactionInvoicedStatus.INVOICED, "Invoiced");
		return map;
	}

	public static Map<Integer, String> getTransactionTypes() {
		Map<Integer, String> map = new HashMap<>();
		map.put(TransactionType.DI, "DI");
		map.put(TransactionType.PI, "PI");
		map.put(TransactionType.SI, "SI");
		return map;
	}

	public static Map<Integer, String> getSampleSentTypes() {
		Map<Integer, String> map = new HashMap<>();
		map.put(SampleSentType.TYPE, "TYPE");
		map.put(SampleSentType.PSS, "PSS");
		return map;
	}
    
    public static String getSampleSentApprovalStatus(Byte id) {
    	Map<Byte, String> statuses = getApprovalStatuses();
    	try {
    		return statuses.get(id);
    	} catch(Exception e) {
    		return statuses.get(defaultStatusNumber);
    	}
    }
    
    public static String getSampleSentSendingStatus(Byte id) {
    	Map<Byte, String> statuses = getSendingStatuses();
    	try {
    		return statuses.get(id);
    	} catch(Exception e) {
    		return statuses.get(defaultStatusNumber);
    	}
    }
    
    
    public static String getInstructionRequestStatus(Byte id) {
    	Map<Byte, String> statuses = getRequestStatuses();
    	try {
    		return statuses.get(id);
    	} catch(Exception e) {
    		return statuses.get(defaultStatusNumber);
    	}
    }
    
    
    public static String getInstructionCompletionStatus(Byte id) {
    	Map<Byte, String> statuses = getCompletionStatuses();
    	try {
    		return statuses.get(id);
    	} catch(Exception e) {
    		return statuses.get(defaultStatusNumber);
    	}
    }
    
    public static Map<Integer, String> getSampleSentShipmentStatuses() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        result.put(ShipmentStatus.COMPLETED, "Completed");
        result.put(ShipmentStatus.PENDING, "Pending");
        return result;
    }
    
    
    
}
