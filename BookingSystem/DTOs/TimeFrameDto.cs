using BookingSystem.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.DTOs
{
    public class TimeFrameDto
    {
        [Required]
        [DataType(DataType.DateTime, ErrorMessage = "Plese enter a valid time")]
        public DateTime StartTime { get; set; }

        [Required]
        [DataType(DataType.DateTime, ErrorMessage = "Plese enter a valid time")]
        public DateTime EndTime { get; set; }

        public TimeFrame GetTimeFrame()
        {
            return new TimeFrame()
            {
                StartTime = this.StartTime,
                EndTime = this.EndTime
            };
        }
    }
}
