// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    //throw new UnsupportedOperationException("TODO: Implement this method.");
   //sortedEvents is not sorted at the moment
   Collection<Event> sortedEvents = events;
   long meetingLength = request.getDuration();
   Collection<String> meetingAttendees = request.getAttendees();

//if duration for a meeting is greater than 1 day
   if (meetingLength > TimeRange.WHOLE_DAY.duration()){
       Collection<TimeRange> noGoodTimes = new ArrayList<>();
       return noGoodTimes;
   }

   List<TimeRange> attendeeTimeRanges = new ArrayList<TimeRange>();

//Get all time ranges that include meeting attendees in other events
for (Event ev : sortedEvents){
	for (String evAttendee : ev.getAttendees()){
		if(meetingAttendees.contains(evAttendee)){
            attendeeTimeRanges.add(ev.getWhen());
            break;
        }
    }
}

//Condense overlaps into fewest number of time ranges
attendeeTimeRanges = condenser(attendeeTimeRanges, new ArrayList<TimeRange>());

//re-sort attendeeTimeRanges
//attendeeTimeRanges = 
Collections.sort(attendeeTimeRanges, TimeRange.ORDER_BY_START);

Collection<TimeRange> solutionTimeRanges = new ArrayList<>();

//solutionTimeRanges becomes set of TimeRanges that 
//don't involve the TimeRanges in attendeeTimeRanges
solutionTimeRanges = getTimesThatWork(TimeRange.START_OF_DAY, attendeeTimeRanges, solutionTimeRanges);

//Filter out from solutionTimeRanges the solution ranges that 
//do not have a long enough duration.
Collection<TimeRange> FinalSolutionTimeRanges = new ArrayList<>();

for (TimeRange tr : solutionTimeRanges){
    if (tr.duration() >= meetingLength){
        FinalSolutionTimeRanges.add(tr);
    }
}
//^^changed from less than and remove to geq and add
//And changed to FinalSolutionTimeRanges for output
return FinalSolutionTimeRanges;
  }

  private Collection<TimeRange> getTimesThatWork(int startTime, 
  List<TimeRange> attendeeTimeRanges, 
  Collection<TimeRange> solutionTimeRanges){
      if(attendeeTimeRanges.size() != 0){
      TimeRange firstElm = attendeeTimeRanges.get(0);
        if(firstElm.start() != TimeRange.START_OF_DAY || 
          firstElm.end() != TimeRange.END_OF_DAY){
              solutionTimeRanges.add(
                  TimeRange.fromStartEnd(
                      startTime, firstElm.start(), false));
          }
            attendeeTimeRanges.remove(firstElm);
           return getTimesThatWork(firstElm.end(), attendeeTimeRanges, solutionTimeRanges);
             
      }
      else{ if (startTime != TimeRange.END_OF_DAY){
          solutionTimeRanges.add(TimeRange.fromStartEnd(startTime, TimeRange.END_OF_DAY, true));
      }
          return solutionTimeRanges;
      } 
      //Should not reach here? 
      //return solutionTimeRanges; 
  }

private List<TimeRange> condenser(
    List<TimeRange> attendeeTimeRanges, 
    List<TimeRange> soln){
        boolean wasChanged = false;
  for (TimeRange t : attendeeTimeRanges){
      boolean noToverlap = false;
    for(TimeRange s : attendeeTimeRanges){
        if(!t.equals(s) && t.overlaps(s)){
            soln.add(
                TimeRange.fromStartEnd(
                    Math.min(s.start(), t.start()), 
                    Math.max(s.end(), t.end()), false));
                    //attendeeTimeRanges.remove(s);
                    //attendeeTimeRanges.remove(t);
                    wasChanged = true;
                    noToverlap = true;
        }
    }
    if (!noToverlap){
soln.add(t);
    }
}
if (wasChanged){
    return condenser(soln, new ArrayList<TimeRange>());
} else {
    return soln;
}
}


}
